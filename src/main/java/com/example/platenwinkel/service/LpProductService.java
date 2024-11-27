package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.LpProductInputDto;
import com.example.platenwinkel.dtos.mapper.LpProductMapper;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.exceptions.DuplicateRecordException;
import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.repositories.LpProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LpProductService {
    private final LpProductRepository lpProductRepository;


    public LpProductService(LpProductRepository lpProductRepository) {
        this.lpProductRepository = lpProductRepository;
    }

    public List<LpProductOutputDto> getAllLps() {
        return lpProductRepository.findAll().stream()
                .map(LpProductMapper::fromModelToOutputDto)
                .collect(Collectors.toList());
    }


    public List<LpProductOutputDto> getAllLpProductsByArtist(String artist) {
        List<LpProduct> lpProductlist = lpProductRepository.findAllLpProductsByArtistEqualsIgnoreCase(artist);
        List<LpProductOutputDto> lpDtoList = new ArrayList<>();

        for (LpProduct lp : lpProductlist) {
            LpProductOutputDto dto = LpProductMapper.fromModelToOutputDto(lp);
            lpDtoList.add(dto);
        }
        return lpDtoList;
    }

    public LpProductOutputDto getLpProductById(Long id) {
        LpProduct lpProduct = lpProductRepository.findById(id)
      .orElseThrow(() -> new RecordNotFoundException("No LP product found with ID: " + id));
        return LpProductMapper.fromModelToOutputDto(lpProduct);
    }

    public LpProductOutputDto addLpProduct(LpProductInputDto lpProductInputDto) {

        validateLpProductInput(lpProductInputDto);


        checkForDuplicateProduct(lpProductInputDto);

        LpProduct lpProduct = LpProductMapper.fromInputDtoToModel(lpProductInputDto);
        LpProduct savedProduct = lpProductRepository.save(lpProduct);

        // Mapping naar output DTO
        return LpProductMapper.fromModelToOutputDto(savedProduct);
    }

    private void validateLpProductInput(LpProductInputDto lpProductInputDto) {
        if (lpProductInputDto.getArtist() == null || lpProductInputDto.getArtist().isBlank()) {
            throw new InvalidInputException("Artist field cannot be empty.");
        }
        if (lpProductInputDto.getPriceInclVat() == null || lpProductInputDto.getPriceInclVat() <= 0) {
            throw new InvalidInputException("Price (including VAT) must be greater than 0.");
        }
        if (lpProductInputDto.getAlbum() == null || lpProductInputDto.getAlbum().isBlank()) {
            throw new InvalidInputException("Album field cannot be empty.");
        }
    }

    private void checkForDuplicateProduct(LpProductInputDto lpProductInputDto) {
        boolean productExists = lpProductRepository.existsByAlbumAndArtist(
                lpProductInputDto.getAlbum(), lpProductInputDto.getArtist()
        );
        if (productExists) {
            throw new DuplicateRecordException("An LP product with the same album and artist already exists.");
        }
    }

    public LpProductOutputDto updateLpProduct(Long id, LpProductInputDto lpProductInputDto) {
        LpProduct existingLpProduct = lpProductRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No LP product found with ID: " + id));

            existingLpProduct.setArtist(lpProductInputDto.getArtist());
            existingLpProduct.setAlbum(lpProductInputDto.getAlbum());
            existingLpProduct.setDescription(lpProductInputDto.getDescription());
            existingLpProduct.setGenre(lpProductInputDto.getGenre());
            existingLpProduct.setInStock(lpProductInputDto.getInStock());
            existingLpProduct.setPriceInclVat(lpProductInputDto.getPriceInclVat());
            existingLpProduct.setPriceEclVat(lpProductInputDto.getPriceEclVat());

        LpProduct updatedProduct = lpProductRepository.save(existingLpProduct);
        return LpProductMapper.fromModelToOutputDto(updatedProduct);
        }


    public void deletelpproduct(@RequestBody Long id) {
        if (!lpProductRepository.existsById(id)) {
            throw new RecordNotFoundException("No LP product found with ID: " + id);
        }
        lpProductRepository.deleteById(id);
    }
}
