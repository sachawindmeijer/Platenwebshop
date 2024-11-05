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
        // Arrange: Create input DTO and mock product data
        ReportInputDto reportInputDto = new ReportInputDto();
        reportInputDto.setComment("Monthly Sales Report"); // Ensure this is set

        LpProduct lpProduct1 = new LpProduct(1L, "The Beatles", "Abbey Road", "Classic album from 1969", Genre.ROCK, 10, 16.80);
        LpProduct lpProduct2 = new LpProduct(2L, "Pink Floyd", "The Wall", "Legendary album from 1979", Genre.ROCK, 5, 18.50);

        // Create orders with items and quantities
        Map<LpProduct, Integer> order1Items = new HashMap<>();
        order1Items.put(lpProduct1, 15); // High sales for lpProduct1

        Map<LpProduct, Integer> order2Items = new HashMap<>();
        order2Items.put(lpProduct2, 5); // Low sales for lpProduct2

        Order order1 = new Order(null, LocalDate.now(), 1, DeliveryStatus.SHIPPED, "123 Music Ave", order1Items);
        Order order2 = new Order(null, LocalDate.now(), 1, DeliveryStatus.SHIPPED, "456 Melody Rd", order2Items);

        List<Order> orders = List.of(order1, order2);

        // Mocking order repository to return the orders
        when(orderRepository.findAll()).thenReturn(orders);

        // Mocking report repository to return a saved report
        Report savedReport = new Report(List.of(lpProduct1), List.of(lpProduct2), 344.5);
        savedReport.setComment("Monthly Sales Report"); // Ensure the saved report has the comment set
        when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

        // Act: Create the report
        ReportOutputDto result = reportService.createReport(reportInputDto);

        // Assert: Check that the report is not null and has correct data
        assertNotNull(result, "ReportOutputDto should not be null");

        // Check total revenue
        double expectedTotalRevenue = (15 * 16.80) + (5 * 18.50);
        assertEquals(expectedTotalRevenue, result.getTotalRevenue(), 0.01, "Total revenue should be correctly calculated");

        // Check top-selling products
        assertEquals(1, result.getTopSellingProducts().size(), "There should be 1 top-selling product");
        assertEquals("The Beatles", result.getTopSellingProducts().get(0).getArtist(), "Top-selling product should be 'The Beatles'");
        assertEquals("Abbey Road", result.getTopSellingProducts().get(0).getAlbum(), "Top-selling product album should be 'Abbey Road'");

        // Check low-selling products
        assertEquals(1, result.getLowSellingProducts().size(), "There should be 1 low-selling product");
        assertEquals("Pink Floyd", result.getLowSellingProducts().get(0).getArtist(), "Low-selling product should be 'Pink Floyd'");
        assertEquals("The Wall", result.getLowSellingProducts().get(0).getAlbum(), "Low-selling product album should be 'The Wall'");

        // Check report comment
        assertEquals(reportInputDto.getComment(), result.getComment(), "The report comment should match the input DTO comment");
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

        List<LpProduct> topSellingProducts = List.of(lpProduct);
        List<LpProduct> lowSellingProducts = List.of();
        double totalRevenue = 150.0;
        LocalDate generatedDate = LocalDate.now();

        Report report = new Report(topSellingProducts, lowSellingProducts, totalRevenue);
        report.setId(id);

        when(reportRepository.findById(id)).thenReturn(Optional.of(report));

        // Act
        ReportOutputDto result = reportService.getReportById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(2L, result.getTopSellingProducts().get(0).getId()); // Assert product ID
        assertEquals("Artist Name", result.getTopSellingProducts().get(0).getArtist()); // Assert artist name
        assertEquals("Album Name", result.getTopSellingProducts().get(0).getAlbum()); // Assert album name
        assertEquals(totalRevenue, result.getTotalRevenue(), 0.01); // Assert total revenue
        assertEquals(generatedDate, result.getRapportDatum()); // Assert generated date
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
