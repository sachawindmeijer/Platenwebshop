package com.example.platenwinkel.controllers;

import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.output.ReportOutputDto;
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


    @PostMapping
    public ResponseEntity<ReportOutputDto> createReport(@RequestBody ReportInputDto reportInputDto) {
        ReportOutputDto createdReport = reportService.createReport(reportInputDto);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }
@GetMapping
public ResponseEntity<List<ReportOutputDto>>getAllReports(){

        return ResponseEntity.ok(reportService.getAllReports());
}


    @GetMapping("/{id}")
    public ResponseEntity<ReportOutputDto> getReportById(@PathVariable Long id) {
        try {
            ReportOutputDto report = reportService.getReportById(id);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        try {
            boolean isDeleted = reportService.deleteReport(id);
            if (isDeleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}


