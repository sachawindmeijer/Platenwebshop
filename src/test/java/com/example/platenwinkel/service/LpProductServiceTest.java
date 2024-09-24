package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.LpProductInputDto;
import com.example.platenwinkel.dtos.mapper.LpProductMapper;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.enumeration.Genre;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LpProductServiceTest {

    @Mock
    LpProductRepository lpProductRepository;

    @InjectMocks
    LpProductService lpProductService;

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
}