package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.LpProductInputDto;
import com.example.platenwinkel.dtos.mapper.LpProductMapper;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.enumeration.Genre;
import com.example.platenwinkel.exceptions.DuplicateRecordException;
import com.example.platenwinkel.exceptions.InvalidInputException;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        lpProductInputDto.setPriceExclVat(16.80);

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

        assertEquals(16.80, result.getPriceExclVat(), 0.01);
    }
    @Test
    @DisplayName("should throw DuplicateRecordException when adding a duplicate LP product")
    void addLpProduct_Duplicate() {
        // Arrange
        LpProductInputDto lpProductInputDto = new LpProductInputDto();
        lpProductInputDto.setArtist("The Beatles");
        lpProductInputDto.setAlbum("Abbey Road");
        lpProductInputDto.setDescription("Classic album from 1969");
        lpProductInputDto.setGenre(Genre.ROCK);
        lpProductInputDto.setInStock(10);
        lpProductInputDto.setPriceExclVat(16.80);

        // Mock that a product with the same artist and album already exists
        Mockito.when(lpProductRepository.existsByAlbumAndArtist("Abbey Road", "The Beatles")).thenReturn(true);

        // Act & Assert
        DuplicateRecordException exception = assertThrows(
                DuplicateRecordException.class,
                () -> lpProductService.addLpProduct(lpProductInputDto)
        );

        // Assert error message
        assertEquals("An LP product with the same album and artist already exists.", exception.getMessage());
    }
    @Test
    @DisplayName("should throw exception for invalid LP product input")
    void addLpProduct_InvalidInput() {
        // Arrange
        LpProductInputDto invalidDto = new LpProductInputDto();
        invalidDto.setArtist(""); // Invalid artist
        invalidDto.setPriceExclVat(-10.0); // Invalid price

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> lpProductService.addLpProduct(invalidDto));
    }

    @Test
    @DisplayName("should update an existing LP product")
    void updateLpProduct_ExistingProduct() {
        // Arrange
        Long id = 1L;
        LpProduct existingProduct = new LpProduct(id, "The Beatles", "Abbey Road", "Classic album", Genre.ROCK, 10, 16.80);
        LpProductInputDto updateDto = new LpProductInputDto();
        updateDto.setArtist("The Beatles");
        updateDto.setAlbum("Revised Abbey Road");
        updateDto.setDescription("Updated description");
        updateDto.setGenre(Genre.ROCK);
        updateDto.setInStock(20);
        updateDto.setPriceExclVat(18.00);

        Mockito.when(lpProductRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        Mockito.when(lpProductRepository.save(Mockito.any(LpProduct.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        LpProductOutputDto result = lpProductService.updateLpProduct(id, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("The Beatles", result.getArtist());
        assertEquals("Revised Abbey Road", result.getAlbum());
        assertEquals("Updated description", result.getDescription());
        assertEquals(20, result.getInStock());
        assertEquals(18.00, result.getPriceExclVat(), 0.01);
    }

    @Test
    @DisplayName("should throw exception when updating non-existing LP product")
    void updateLpProduct_NotFound() {
        // Arrange
        Long id = 99L;
        LpProductInputDto inputDto = new LpProductInputDto();
        inputDto.setArtist("Artist Name");  // Initialize fields properly
        inputDto.setAlbum("Album Name");
        inputDto.setPriceExclVat(10.0);
        inputDto.setInStock(10);
        Mockito.when(lpProductRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> lpProductService.updateLpProduct(id, inputDto));
    }

    @Test
    @DisplayName("should throw InvalidInputException for invalid input during update")
    void updateLpProduct_InvalidInput() {
        // Arrange
        Long id = 1L;
        LpProductInputDto invalidDto = new LpProductInputDto();
        invalidDto.setArtist(""); // Invalid artist
        invalidDto.setPriceExclVat(-10.0); // Invalid price

        // Lenient stub for findById() in case it's needed for other test cases
        Mockito.lenient().when(lpProductRepository.findById(id)).thenReturn(Optional.of(new LpProduct()));

        // Act & Assert
        InvalidInputException exception = assertThrows(
                InvalidInputException.class,
                () -> lpProductService.updateLpProduct(id, invalidDto)
        );

        // Assert error message
        assertEquals("Artist field cannot be empty.", exception.getMessage()); // Expected message for invalid artist
    } // Expected message for invalid artist

    @Test
    void updateLpProduct_FindById() {
        // Arrange
        Long id = 1L;
        LpProduct existingProduct = new LpProduct(id, "The Beatles", "Abbey Road", "Classic album", Genre.ROCK, 10, 16.80);

        Mockito.when(lpProductRepository.findById(id)).thenReturn(Optional.of(existingProduct));

        // Act
        Optional<LpProduct> result = lpProductRepository.findById(id);

        // Assert
        assertTrue(result.isPresent(), "Expected product to be found");
        assertEquals(id, result.get().getId(), "Product ID should match");
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
    @DisplayName("should return an empty list when no products are found for the artist")
    void getAllLpProductsByArtist_NoProductsFound() {
        // Arrange
        String artist = "Unknown Artist";
        Mockito.when(lpProductRepository.findAllLpProductsByArtistEqualsIgnoreCase(artist)).thenReturn(Collections.emptyList());

        // Act
        List<LpProductOutputDto> result = lpProductService.getAllLpProductsByArtist(artist);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("should throw InvalidInputException when artist is null or blank")
    void getAllLpProductsByArtist_InvalidInput() {
        // Arrange
        String invalidArtist = "";

        // Act & Assert
        InvalidInputException exception = assertThrows(
                InvalidInputException.class,
                () -> lpProductService.getAllLpProductsByArtist(invalidArtist)
        );

        assertEquals("Artist field cannot be empty.", exception.getMessage());
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

        assertEquals("No LP product found with ID: 99", exception.getMessage());
    }

    @Test
    @DisplayName("should delete LP product by id")
    void deleteLpProduct() {
        // Arrange
        Long id = 1L;
        Mockito.when(lpProductRepository.existsById(id)).thenReturn(true);

        // Act
        lpProductService.deletelpproduct(id);

        // Assert
        Mockito.verify(lpProductRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("should throw exception when deleting non-existing LP product")
    void deleteLpProduct_NotFound() {
        // Arrange
        Long id = 99L;
        Mockito.when(lpProductRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> lpProductService.deletelpproduct(id));
    }

    @Test
    @DisplayName("should delete an existing LP product")
    void deleteLpProduct_ExistingProduct() {
        // Arrange
        Long id = 1L;
        Mockito.when(lpProductRepository.existsById(id)).thenReturn(true);

        // Act
        lpProductService.deletelpproduct(id);

        // Assert
        Mockito.verify(lpProductRepository, Mockito.times(1)).deleteById(id);
    }

}