package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.output.ReportOutputDto;
import com.example.platenwinkel.enumeration.Genre;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Report;
import com.example.platenwinkel.repositories.LpProductRepository;
import com.example.platenwinkel.repositories.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private LpProductRepository lpProductRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    @DisplayName("should return all reports")
    void getAllReports() {
        // Arrange
        LpProduct lpProduct1 = new LpProduct(1L, "The Beatles", "Abbey Road", "Classic album from 1969", Genre.ROCK, 10, 16.80);
        LpProduct lpProduct2 = new LpProduct(2L, "Pink Floyd", "The Wall", "Legendary album from 1979", Genre.ROCK, 5, 18.50);

        Report report1 = new Report();
        report1.setId(1L);  // Set the ID
        report1.setLpProduct(lpProduct1);
        report1.setVoorraadAantal(50);
        report1.setVerkochtAantal(20);
        report1.setRapportDatum(LocalDateTime.now());

        Report report2 = new Report();
        report2.setId(2L);  // Set the ID
        report2.setLpProduct(lpProduct2);
        report2.setVoorraadAantal(30);
        report2.setVerkochtAantal(10);
        report2.setRapportDatum(LocalDateTime.now().minusDays(1));

        List<Report> reports = List.of(report1, report2);

        when(reportRepository.findAll()).thenReturn(reports);

        // Act
        List<ReportOutputDto> result = reportService.getAllReports();

        // Assert
        assertEquals(2, result.size());
        assertNotNull(result);

        // Assertions for the first report
        assertEquals(1L, result.get(0).getId());
        assertEquals(1L, result.get(0).getProductId()); // Product ID
        assertEquals("The Beatles", result.get(0).getArtist());
        assertEquals("Abbey Road", result.get(0).getAlbum());
        assertEquals(50, result.get(0).getVoorraadAantal());
        assertEquals(20, result.get(0).getVerkochtAantal());
        assertNotNull(result.get(0).getRapportDatum());

        // Assertions for the second report
        assertEquals(2L, result.get(1).getId());
        assertEquals(2L, result.get(1).getProductId()); // Product ID
        assertEquals("Pink Floyd", result.get(1).getArtist());
        assertEquals("The Wall", result.get(1).getAlbum());
        assertEquals(30, result.get(1).getVoorraadAantal());
        assertEquals(10, result.get(1).getVerkochtAantal());
        assertNotNull(result.get(1).getRapportDatum());
    }

    @Test
    @DisplayName("should return report by id")
    void getReportById() {
        // Arrange
        Long id = 1L;

        LpProduct lpProduct = new LpProduct();
        lpProduct.setId(2L); // Set the id of the product
        lpProduct.setArtist("Artist Name"); // Set the artist name
        lpProduct.setAlbum("Album Name"); // Set the album name


        Report report = new Report( lpProduct, 110, 40, LocalDateTime.now ());
        report.setId(id);

        when(reportRepository.findById(id)).thenReturn(Optional.of(report));

        // Act
        ReportOutputDto result = reportService.getReportById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(lpProduct.getId(), result.getProductId()); // Assert the product ID
        assertEquals(lpProduct.getArtist(), result.getArtist()); // Assert the artist name
        assertEquals(lpProduct.getAlbum(), result.getAlbum()); // Assert the album name
        assertEquals(report.getVoorraadAantal(), result.getVoorraadAantal()); // Assert voorraad aantal
        assertEquals(report.getVerkochtAantal(), result.getVerkochtAantal()); // Assert verkocht aantal
        assertEquals(report.getRapportDatum(), result.getRapportDatum()); // Assert rapport datum
    }

    @Test
    @DisplayName("should throw exception when report not found by id")
    void getReportById_NotFound() {
        // Arrange
        Long id = 99L;
        when(reportRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reportService.getReportById(id);
        });

        assertEquals("Report not found with id: " + id, exception.getMessage());
    }

    @Test
    @DisplayName("should create report")
    void createReport() {
        // Arrange
        ReportInputDto reportInputDto = new ReportInputDto();
        reportInputDto.setVoorraadAantal(100);
        reportInputDto.setVerkochtAantal(50);
        reportInputDto.setRapportDatum(LocalDateTime.now());


        Long productId = 1L;
        LpProduct lpProduct = new LpProduct(productId, "Madonna", "Like a Virgin", "is top pop", Genre.POP, 6, 19.99);
        when(lpProductRepository.findById(productId)).thenReturn(Optional.of(lpProduct));

        Report report = new Report(lpProduct, reportInputDto.getVoorraadAantal(), reportInputDto.getVerkochtAantal(), reportInputDto.getRapportDatum());
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        // Act
        ReportOutputDto result = reportService.createReport(reportInputDto, productId);

        // Assert
        assertNotNull(result);
        assertNotNull(result);
        assertEquals(report.getId(), result.getId());
        assertEquals(lpProduct.getId(), result.getProductId());
        assertEquals(lpProduct.getArtist(), result.getArtist());
        assertEquals(lpProduct.getAlbum(), result.getAlbum());
        assertEquals(report.getVoorraadAantal(), result.getVoorraadAantal());
        assertEquals(report.getVerkochtAantal(), result.getVerkochtAantal());
        assertEquals(report.getRapportDatum(), result.getRapportDatum());
    }

    @Test
    @DisplayName("should throw exception when creating report with non-existent product")
    void createReport_ProductNotFound() {
        // Arrange
        ReportInputDto reportInputDto = new ReportInputDto(/* set fields */);
        Long productId = 99L;
        when(lpProductRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reportService.createReport(reportInputDto, productId);
        });

        assertEquals("Product not found with id: " + productId, exception.getMessage());
    }

    @Test
    @DisplayName("should delete report")
    void deleteReport() {
        // Arrange
        Long id = 1L;
        when(reportRepository.existsById(id)).thenReturn(true);

        // Act
        reportService.deleteReport(id);

        // Assert
        verify(reportRepository, times(1)).deleteById(id);
    }

}
