package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.mapper.ReportMapper;
import com.example.platenwinkel.dtos.output.ReportOutputDto;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Report;
import com.example.platenwinkel.repositories.LpProductRepository;
import com.example.platenwinkel.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final LpProductRepository lpProductRepository;


    public ReportService(ReportRepository repportRepository, LpProductRepository lpProductRepository) {
        this.reportRepository = repportRepository;
        this.lpProductRepository = lpProductRepository;
    }

    public List<ReportOutputDto> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(ReportMapper::fromModelToOutputDto)
                .collect(Collectors.toList());
    }

    public ReportOutputDto getReportById(Long id) {
        Optional<Report> optionalReport = reportRepository.findById(id);

        if (optionalReport.isPresent()) {
            return ReportMapper.fromModelToOutputDto(optionalReport.get());
        } else {
            throw new RuntimeException("Report not found with id: " + id);
        }
    }

    public ReportOutputDto createReport(ReportInputDto reportInputDto, Long productId) {
        Optional<LpProduct> optionalLpProduct = lpProductRepository.findById(productId);

        if (optionalLpProduct.isPresent()) {
            LpProduct lpProduct = optionalLpProduct.get();
            Report report = ReportMapper.fromInputDtoToMode(reportInputDto, lpProduct);
            Report savedReport = reportRepository.save(report);
            return ReportMapper.fromModelToOutputDto(savedReport);
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }

    public void deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
        } else {
            throw new RuntimeException("Report not found with id: " + id);
        }
    }
}


