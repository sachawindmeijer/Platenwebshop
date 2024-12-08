package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.mapper.OrderMapper;
import com.example.platenwinkel.dtos.mapper.ReportMapper;
import com.example.platenwinkel.dtos.output.OrderOutputDto;
import com.example.platenwinkel.dtos.output.ReportOutputDto;
import com.example.platenwinkel.exceptions.BadRequestException;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
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

        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new RecordNotFoundException("No orders found to generate the report.");
        }

        Map<LpProduct, Integer> productSales = orders.stream()
                .flatMap(order -> order.getItems().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ));

        if (productSales.isEmpty()) {
            throw new RecordNotFoundException("No products found in orders.");
        }

        List<LpProduct> topSellingProducts = productSales.entrySet().stream()
                .filter(entry -> entry.getValue() >= 10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<LpProduct> lowSellingProducts = productSales.entrySet().stream()
                .filter(entry -> entry.getValue() < 10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        double totalRevenue = orders.stream()
                .mapToDouble(Order::getTotalOrderAmount)
                .sum();

        if (totalRevenue == 0) {
            throw new BadRequestException("Cannot generate report, total revenue is zero.");
        }

        Report report = ReportMapper.fromInputDtoToMode(reportInputDto, topSellingProducts, lowSellingProducts, totalRevenue);

        Report savedReport = reportRepository.save(report);

        return ReportMapper.fromModelToOutputDto(savedReport);
    }
    public List<ReportOutputDto> getAllReports() {
        List<Report> reportList = reportRepository.findAll();
        List<ReportOutputDto> reportDtoList = new ArrayList<>();

        for (Report report : reportList) {
            ReportOutputDto dto = ReportMapper.fromModelToOutputDto(report);
            reportDtoList.add(dto);
        }

        return reportDtoList;
    }


    public ReportOutputDto getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Report not found with id: " + id));
        return ReportMapper.fromModelToOutputDto(report);
    }


    public boolean deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("Report not found with id: " + id);
        }
    }
}