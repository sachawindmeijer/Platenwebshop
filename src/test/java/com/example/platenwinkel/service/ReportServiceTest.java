package com.example.platenwinkel.service;


import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.output.ReportOutputDto;
import com.example.platenwinkel.enumeration.DeliveryStatus;
import com.example.platenwinkel.enumeration.Genre;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.models.Report;
import com.example.platenwinkel.repositories.LpProductRepository;
import com.example.platenwinkel.repositories.OrderRepository;
import com.example.platenwinkel.repositories.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private LpProductRepository lpProductRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    @DisplayName("should generate a sales report with top and low selling products")
    void createReport() {
        // Arrange:
        ReportInputDto reportInputDto = new ReportInputDto();
        reportInputDto.setComment("Monthly Sales Report");

        LpProduct lpProduct1 = new LpProduct(1L, "The Beatles", "Abbey Road", "Classic album from 1969", Genre.ROCK, 10, 16.80);
        LpProduct lpProduct2 = new LpProduct(2L, "Pink Floyd", "The Wall", "Legendary album from 1979", Genre.ROCK, 5, 18.50);


        Map<LpProduct, Integer> order1Items = new HashMap<>();
        order1Items.put(lpProduct1, 15);

        Map<LpProduct, Integer> order2Items = new HashMap<>();
        order2Items.put(lpProduct2, 5);

        Order order1 = new Order(null, LocalDate.now(), 1, DeliveryStatus.SHIPPED, "123 Music Ave", order1Items);
        Order order2 = new Order(null, LocalDate.now(), 1, DeliveryStatus.SHIPPED, "456 Melody Rd", order2Items);

        List<Order> orders = List.of(order1, order2);


        when(orderRepository.findAll()).thenReturn(orders);

        Report savedReport = new Report(List.of(lpProduct1), List.of(lpProduct2), 344.5);
        savedReport.setComment("Monthly Sales Report");
        when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

        // Act:
        ReportOutputDto result = reportService.createReport(reportInputDto);

        // Assert:
        assertNotNull(result, "ReportOutputDto should not be null");


        Double expectedTotalRevenue = (15 * 16.80) + (5 * 18.50);
        assertEquals(expectedTotalRevenue, result.getTotalRevenue(), 0.01, "Total revenue should be correctly calculated");


        assertEquals(1, result.getTopSellingProducts().size(), "There should be 1 top-selling product");
        assertEquals("The Beatles", result.getTopSellingProducts().get(0).getArtist(), "Top-selling product should be 'The Beatles'");
        assertEquals("Abbey Road", result.getTopSellingProducts().get(0).getAlbum(), "Top-selling product album should be 'Abbey Road'");


        assertEquals(1, result.getLowSellingProducts().size(), "There should be 1 low-selling product");
        assertEquals("Pink Floyd", result.getLowSellingProducts().get(0).getArtist(), "Low-selling product should be 'Pink Floyd'");
        assertEquals("The Wall", result.getLowSellingProducts().get(0).getAlbum(), "Low-selling product album should be 'The Wall'");


        assertEquals(reportInputDto.getComment(), result.getComment(), "The report comment should match the input DTO comment");
    }
    @Test
    @DisplayName("should return report by id")
    void getReportById() {
        // Arrange
        Long id = 1L;

        LpProduct lpProduct = new LpProduct();
        lpProduct.setId(2L);
        lpProduct.setArtist("Artist Name");
        lpProduct.setAlbum("Album Name");

        List<LpProduct> topSellingProducts = List.of(lpProduct);
        List<LpProduct> lowSellingProducts = List.of();
        Double totalRevenue = 150.0;
        LocalDate generatedDate = LocalDate.now();

        Report report = new Report(topSellingProducts, lowSellingProducts, totalRevenue);
        report.setId(id);

        when(reportRepository.findById(id)).thenReturn(Optional.of(report));

        // Act
        ReportOutputDto result = reportService.getReportById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(2L, result.getTopSellingProducts().get(0).getId());
        assertEquals("Artist Name", result.getTopSellingProducts().get(0).getArtist());
        assertEquals("Album Name", result.getTopSellingProducts().get(0).getAlbum());
        assertEquals(totalRevenue, result.getTotalRevenue(), 0.01);
        assertEquals(generatedDate, result.getRapportDatum());
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
