package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.mapper.OrderMapper;
import com.example.platenwinkel.dtos.mapper.ReportMapper;
import com.example.platenwinkel.dtos.output.OrderOutputDto;
import com.example.platenwinkel.dtos.output.ReportOutputDto;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.models.Report;
import com.example.platenwinkel.repositories.LpProductRepository;
import com.example.platenwinkel.repositories.OrderRepository;
import com.example.platenwinkel.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final LpProductRepository lpProductRepository;
    private final OrderRepository orderRepository;


    public ReportService(ReportRepository reportRepository, LpProductRepository lpProductRepository, OrderRepository orderRepository) {
        this.reportRepository = reportRepository;
        this.lpProductRepository = lpProductRepository;
        this.orderRepository = orderRepository;
    }

    public ReportOutputDto createReport(ReportInputDto reportInputDto) {
        // Fetch all orders to analyze sales data
        List<Order> orders = orderRepository.findAll();

        // Calculate product sales count
        Map<LpProduct, Integer> productSales = orders.stream()
                .flatMap(order -> order.getItems().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum // In case of duplicate products, sum their quantities
                ));

        // Identify top-selling and low-selling products
        List<LpProduct> topSellingProducts = productSales.entrySet().stream()
                .filter(entry -> entry.getValue() >= 10) // Threshold for top-selling
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<LpProduct> lowSellingProducts = productSales.entrySet().stream()
                .filter(entry -> entry.getValue() < 10) // Threshold for low-selling
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Calculate total revenue from all orders
        double totalRevenue = orders.stream()
                .mapToDouble(Order::getTotalOrderAmount)
                .sum();

        // Create a Report model using the calculated data and input DTO comment
        Report report = ReportMapper.fromInputDtoToMode(reportInputDto, topSellingProducts, lowSellingProducts, totalRevenue);

        // Save the report in the database
        Report savedReport = reportRepository.save(report);

        // Convert the saved report to an output DTO
        return ReportMapper.fromModelToOutputDto(savedReport);
    }
    public List<ReportOutputDto> getAllReports() {
        // Fetch all reports from the repository
        List<Report> reportList = reportRepository.findAll();
        List<ReportOutputDto> reportDtoList = new ArrayList<>();

        // Convert each Report to ReportOutputDto and add to the list
        for (Report report : reportList) {
            ReportOutputDto dto = ReportMapper.fromModelToOutputDto(report);
            reportDtoList.add(dto);
        }

        // Return the list of ReportOutputDto
        return reportDtoList; // Return the correct list here
    }


    public ReportOutputDto getReportById(Long id) {
        Optional<Report> optionalReport = reportRepository.findById(id);

        if (optionalReport.isPresent()) {
            return ReportMapper.fromModelToOutputDto(optionalReport.get());
        } else {
            throw new RuntimeException("Report not found with id: " + id);
        }
    }

    public boolean deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true; // Return true if deletion was successful
        } else {
            throw new RuntimeException("Report not found with id: " + id);
        }
    }
}