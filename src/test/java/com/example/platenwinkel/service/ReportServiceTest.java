package com.example.platenwinkel.service;


import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.output.ReportOutputDto;
import com.example.platenwinkel.enumeration.DeliveryStatus;
import com.example.platenwinkel.enumeration.Genre;
import com.example.platenwinkel.exceptions.BadRequestException;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
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
import java.util.*;

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
    @DisplayName("should generate a report successfully when valid data is provided")
    void createReport_ValidData() {
        // Arrange
        ReportInputDto reportInputDto = new ReportInputDto();
        reportInputDto.setComment("Monthly Report");

        LpProduct lpProduct1 = new LpProduct(1L, "Artist1", "Album1", "Description1", Genre.ROCK, 10, 20.0);
        lpProduct1.setPriceInclVat();
        LpProduct lpProduct2 = new LpProduct(2L, "Artist2", "Album2", "Description2", Genre.POP, 5, 25.0);
        lpProduct2.setPriceInclVat();

        Map<LpProduct, Integer> items1 = Map.of(lpProduct1, 12, lpProduct2, 4);
        Order order1 = new Order(null, LocalDate.now(), "Address1", items1);

        when(orderRepository.findAll()).thenReturn(List.of(order1));

        Report savedReport = new Report(List.of(lpProduct1), List.of(lpProduct2), 300.0);
        savedReport.setComment("Monthly Report");
        when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

        // Act
        ReportOutputDto result = reportService.createReport(reportInputDto);

        // Assert
        assertNotNull(result);
        assertEquals("Monthly Report", savedReport.getComment());
        assertEquals(1, result.getTopSellingProducts().size());
        assertEquals("Artist1", result.getTopSellingProducts().get(0).getArtist());
        assertEquals(1, result.getLowSellingProducts().size());
        assertEquals("Artist2", result.getLowSellingProducts().get(0).getArtist());
    }
    @Test
    @DisplayName("should generate a sales report with top and low selling products")
    void createReport() {
        // Arrange:
        ReportInputDto reportInputDto = new ReportInputDto();
        reportInputDto.setComment("Monthly Sales Report");

        LpProduct lpProduct1 = new LpProduct(1L, "The Beatles", "Abbey Road", "Classic album from 1969", Genre.ROCK, 10, 16.80);
        lpProduct1.setPriceInclVat(); // Calculate priceInclVat
        LpProduct lpProduct2 = new LpProduct(2L, "Pink Floyd", "The Wall", "Legendary album from 1979", Genre.ROCK, 5, 18.50);
        lpProduct2.setPriceInclVat(); // Calculate priceInclVat

        Map<LpProduct, Integer> order1Items = new HashMap<>();
        order1Items.put(lpProduct1, 15);

        Map<LpProduct, Integer> order2Items = new HashMap<>();
        order2Items.put(lpProduct2, 5);

        Order order1 = new Order(null, LocalDate.now(), "123 Music Ave", order1Items);
        Order order2 = new Order(null, LocalDate.now(), "456 Melody Rd", order2Items);

        List<Order> orders = List.of(order1, order2);

        when(orderRepository.findAll()).thenReturn(orders);

        // Calculate revenue dynamically
        double calculatedTotalRevenue = (15 * lpProduct1.getPriceInclVat()) + (5 * lpProduct2.getPriceInclVat());
        Report savedReport = new Report(List.of(lpProduct1), List.of(lpProduct2), calculatedTotalRevenue);
        savedReport.setComment("Monthly Sales Report");
        when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

        // Act:
        ReportOutputDto result = reportService.createReport(reportInputDto);

        // Assert:
        assertNotNull(result, "ReportOutputDto should not be null");

        double expectedTotalRevenue = (15 * lpProduct1.getPriceInclVat()) + (5 * lpProduct2.getPriceInclVat());
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
    @DisplayName("should throw exception when ReportInputDto is empty")
    void createReport_EmptyInputDto() {
        // Arrange
        ReportInputDto emptyInputDto = new ReportInputDto(); // No comment or other fields set
        when(orderRepository.findAll()).thenReturn(Collections.emptyList()); // No orders to process

        // Act & Assert
        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            reportService.createReport(emptyInputDto);
        });

        assertEquals("No orders found to generate the report.", exception.getMessage());
    }
    @Test
    @DisplayName("should throw exception when no orders found in createReport")
    void createReport_NoOrders() {
        // Arrange
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            reportService.createReport(new ReportInputDto());
        });

        assertEquals("No orders found to generate the report.", exception.getMessage());
    }


    @Test
    @DisplayName("should throw exception when reportInputDto is null")
    void createReport_NullInput() {
        // Act & Assert
        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            reportService.createReport(null);
        });

        assertEquals("No orders found to generate the report.", exception.getMessage());
    }

    @Test
    @DisplayName("should create report with no top or low selling products when no sales meet criteria")
    void createReport_NoTopOrLowSellingProducts() {
        // Arrange
        ReportInputDto reportInputDto = new ReportInputDto();
        reportInputDto.setComment("Monthly Report");

        LpProduct lpProduct1 = new LpProduct(1L, "Artist1", "Album1", "Description1", Genre.ROCK, 10, 20.0);
        lpProduct1.setPriceInclVat();
        Map<LpProduct, Integer> items = Map.of(lpProduct1, 9); // Below threshold for top-selling
        Order order = new Order(null, LocalDate.now(), "Address1", items);

        when(orderRepository.findAll()).thenReturn(List.of(order));

        double totalRevenue = items.get(lpProduct1) * lpProduct1.getPriceInclVat();
        Report savedReport = new Report(List.of(), List.of(), totalRevenue);
        savedReport.setComment("Monthly Report");
        when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

        // Act
        ReportOutputDto result = reportService.createReport(reportInputDto);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTopSellingProducts().size());
        assertEquals(0, result.getLowSellingProducts().size());
        assertEquals(totalRevenue, result.getTotalRevenue(), 0.01);
        assertEquals("Monthly Report", result.getComment());
    }

    @Test
    @DisplayName("should throw exception when saving report fails")
    void createReport_SaveFailure() {
        // Arrange
        ReportInputDto reportInputDto = new ReportInputDto();
        reportInputDto.setComment("Monthly Report");

        LpProduct lpProduct1 = new LpProduct(1L, "Artist1", "Album1", "Description1", Genre.ROCK, 10, 20.0);
        lpProduct1.setPriceInclVat();
        Map<LpProduct, Integer> items = Map.of(lpProduct1, 12);
        Order order = new Order(null, LocalDate.now(), "Address1", items);

        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(reportRepository.save(any(Report.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reportService.createReport(reportInputDto);
        });

        assertEquals("Database error", exception.getMessage());
    }
    @Test
    @DisplayName("should throw exception when total revenue is zero in createReport")
    void createReport_ZeroRevenue() {
        // Arrange
        Order order = new Order(null, LocalDate.now(), "123 Music Ave", new HashMap<>());
        when(orderRepository.findAll()).thenReturn(List.of(order));

        // Act & Assert
        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            reportService.createReport(new ReportInputDto());
        });

        assertEquals("No products found in orders.", exception.getMessage());
    }
    @Test
    @DisplayName("should return all reports")
    void getAllReports() {
        // Arrange
        Report report1 = new Report(List.of(), List.of(), 100.0);
        Report report2 = new Report(List.of(), List.of(), 200.0);
        when(reportRepository.findAll()).thenReturn(List.of(report1, report2));

        // Act
        List<ReportOutputDto> result = reportService.getAllReports();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(100.0, result.get(0).getTotalRevenue(), 0.01);
        assertEquals(200.0, result.get(1).getTotalRevenue(), 0.01);
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
    @DisplayName("should return report with no top or low selling products when they are absent")
    void getReportById_NoTopOrLowSellingProducts() {
        // Arrange
        Long reportId = 1L;

        // Create a report with no top or low-selling products
        List<LpProduct> emptyTopSellingProducts = Collections.emptyList();
        List<LpProduct> emptyLowSellingProducts = Collections.emptyList();
        Double totalRevenue = 150.0; // Only revenue is present

        Report report = new Report(emptyTopSellingProducts, emptyLowSellingProducts, totalRevenue);
        report.setId(reportId);

        // Mock the repository to return this report
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        // Act
        ReportOutputDto result = reportService.getReportById(reportId);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(reportId, result.getId(), "The report ID should match");
        assertEquals(0, result.getTopSellingProducts().size(), "Top-selling products should be empty");
        assertEquals(0, result.getLowSellingProducts().size(), "Low-selling products should be empty");
        assertEquals(totalRevenue, result.getTotalRevenue(), 0.01, "Total revenue should match the report data");
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
    @Test
    @DisplayName("should throw exception when deleting a report that does not exist")
    void deleteReport_NotFound() {
        // Arrange
        Long id = 99L;
        when(reportRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            reportService.deleteReport(id);
        });

        assertEquals("Report not found with id: " + id, exception.getMessage());
    }
}


