package com.example.platenwinkel.controllers;

import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.output.ReportOutputDto;
import com.example.platenwinkel.models.Report;
import com.example.platenwinkel.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final  ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<ReportOutputDto>> getAllReports() {
        List<ReportOutputDto> reports = reportService.getAllReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportOutputDto> getReportById(@PathVariable Long id) {
        ReportOutputDto report = reportService.getReportById(id);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PostMapping("/{productId}")
    public  ResponseEntity<ReportOutputDto> createReport(
            @PathVariable Long productId,
            @RequestBody ReportInputDto reportInputDto) {
        ReportOutputDto newReport = reportService.createReport(reportInputDto, productId);
        return new ResponseEntity<>(newReport, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


