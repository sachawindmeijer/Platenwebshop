package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.LpProductInputDto;
import com.example.platenwinkel.dtos.mapper.LpProductMapper;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.enumeration.Genre;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.repositories.LpProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LpProductServiceTest {

    @Mock
    LpProductRepository lpProductRepository;

    @InjectMocks
    LpProductService lpProductService;
//5
    @Test
    @DisplayName("should add Lpproduct")
    void addLpProduct() {
//arange
        LpProductInputDto lpProductInputDto = new LpProductInputDto();
        lpProductInputDto.setArtist("The Beatles");
        lpProductInputDto.setAlbum("Abbey Road");
        lpProductInputDto.setDescription("Classic album from 1969");
        lpProductInputDto.setGenre(Genre.ROCK); // Assuming Genre is an enum
        lpProductInputDto.setInStock(10);
        lpProductInputDto.setPriceEclVat(16.80);

        Mockito.when(lpProductRepository.save(Mockito.any())).thenReturn(LpProductMapper.fromInputDtoToModel(lpProductInputDto));

        //act
        LpProductOutputDto result = lpProductService.addLpProduct(lpProductInputDto);

        //assert
        assertNotNull(result);

        assertEquals("The Beatles", result.getArtist());
        assertEquals("Abbey Road", result.getAlbum());
        assertEquals("Classic album from 1969", result.getDescription());
        assertEquals(Genre.ROCK, result.getGenre());
        assertEquals(10, result.getInStock());

        assertEquals(16.80, result.getPriceEclVat(), 0.01);
    }

    @Test
    @DisplayName("should return all LP products")
    void getAllLps() {
        // Arrange
        LpProduct lp1 = new LpProduct(1L, "The Beatles", "Abbey Road", "Classic album from 1969", Genre.ROCK, 10, 16.80);
        LpProduct lp2 = new LpProduct(2L, "Pink Floyd", "The Wall", "Legendary album from 1979", Genre.ROCK, 5, 18.50);

        List<LpProduct> lpProductList = List.of(lp1, lp2);
        Mockito.when(lpProductRepository.findAll()).thenReturn(lpProductList);

        // Act
        List<LpProductOutputDto> result = lpProductService.getAllLps();

        // Assert
        assertEquals(2, result.size());
        assertEquals("The Beatles", result.get(0).getArtist());
        assertEquals("Abbey Road", result.get(0).getAlbum());
        assertEquals("Pink Floyd", result.get(1).getArtist());
        assertEquals("The Wall", result.get(1).getAlbum());
    }

    @Test
    @DisplayName("should return all LP products by artist")
    void getAllLpProductsByArtist() {
        // Arrange
        String artist = "The Beatles";
        LpProduct lp1 = new LpProduct(1L, artist, "Abbey Road", "Classic album from 1969", Genre.ROCK, 10, 16.80);
        LpProduct lp2 = new LpProduct(2L, artist, "Let It Be", "Final studio album", Genre.ROCK, 5, 17.50);

        List<LpProduct> lpProductList = List.of(lp1, lp2);
        Mockito.when(lpProductRepository.findAllLpProductsByArtistEqualsIgnoreCase(artist)).thenReturn(lpProductList);

        // Act
        List<LpProductOutputDto> result = lpProductService.getAllLpProductsByArtist(artist);

        // Assert
        assertEquals(2, result.size());
        assertEquals(artist, result.get(0).getArtist());
        assertEquals("Abbey Road", result.get(0).getAlbum());
        assertEquals("Let It Be", result.get(1).getAlbum());
    }

    @Test
    @DisplayName("should return LP product by id")
    void getLpProductById() {
        // Arrange
        Long id = 1L;
        LpProduct lp = new LpProduct(id, "The Beatles", "Abbey Road", "Classic album from 1969", Genre.ROCK, 10, 16.80);

        Mockito.when(lpProductRepository.findById(id)).thenReturn(Optional.of(lp));

        // Act
        LpProductOutputDto result = lpProductService.getLpProductById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("The Beatles", result.getArtist());
        assertEquals("Abbey Road", result.getAlbum());
    }

    @Test
    @DisplayName("should throw exception when LP product not found by id")
    void getLpProductById_NotFound() {
        // Arrange
        Long id = 99L;
        Mockito.when(lpProductRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RecordNotFoundException.class, () -> lpProductService.getLpProductById(id));

        assertEquals("geen lpproduct gevonden", exception.getMessage());
    }

}