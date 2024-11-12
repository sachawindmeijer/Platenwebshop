package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.LpProductInputDto;
import com.example.platenwinkel.dtos.mapper.LpProductMapper;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.repositories.LpProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LpProductService {
    private final LpProductRepository lpProductRepository;//depandancies


    public LpProductService(LpProductRepository lpProductRepository) {
        this.lpProductRepository = lpProductRepository;
    }

    public List<LpProductOutputDto> getAllLps() {
        List<LpProduct> lpProductlist = lpProductRepository.findAll();
        List<LpProductOutputDto> lpDtoList = new ArrayList<>();

        for (LpProduct lp : lpProductlist) {
            LpProductOutputDto dto = LpProductMapper.fromModelToOutputDto(lp);
            lpDtoList.add(dto);
        }
        return lpDtoList;
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
        Optional<LpProduct> lpProductOptional = lpProductRepository.findById(id);
        if (lpProductOptional.isPresent()) {
            LpProduct lpProduct = lpProductOptional.get();
            return LpProductMapper.fromModelToOutputDto(lpProduct);
        } else {
            throw new RecordNotFoundException("geen lpproduct gevonden");
        }
    }

    public LpProductOutputDto addLpProduct(LpProductInputDto lpProductInputDto) {
        LpProduct lpProduct = LpProductMapper.fromInputDtoToModel(lpProductInputDto);
        LpProduct savedProduct = lpProductRepository.save(lpProduct);
        return LpProductMapper.fromModelToOutputDto(savedProduct);
    }
    public LpProductOutputDto updateLpProduct(Long id, LpProductInputDto lpProductInputDto) {

        Optional<LpProduct> lpProductOptional = lpProductRepository.findById(id);


        if (lpProductOptional.isPresent()) {
            LpProduct existingLpProduct = lpProductOptional.get();

            existingLpProduct.setArtist(lpProductInputDto.getArtist());
            existingLpProduct.setAlbum(lpProductInputDto.getAlbum());
            existingLpProduct.setDescription(lpProductInputDto.getDescription());
            existingLpProduct.setGenre(lpProductInputDto.getGenre());
            existingLpProduct.setInStock(lpProductInputDto.getInStock());
            existingLpProduct.setPriceInclVat(lpProductInputDto.getPriceInclVat());
            existingLpProduct.setPriceEclVat(lpProductInputDto.getPriceEclVat());

            LpProduct updatedProduct = lpProductRepository.save(existingLpProduct);


            return LpProductMapper.fromModelToOutputDto(updatedProduct);
        } else {

            throw new RecordNotFoundException("Geen LP product gevonden met ID: " + id);
        }
    }

    public void deletelpproduct(@RequestBody Long id) {

        lpProductRepository.deleteById(id);
    }
}
