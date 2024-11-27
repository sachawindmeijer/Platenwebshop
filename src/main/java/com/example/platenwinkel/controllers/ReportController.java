package com.example.platenwinkel.controllers;

import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.output.ReportOutputDto;
import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.helper.BindingResultHelper;
import com.example.platenwinkel.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @PostMapping
    public ResponseEntity<ReportOutputDto> createReport(@Valid @RequestBody ReportInputDto reportInputDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException("Somthing went wrong, please check the following fields. " + BindingResultHelper.getErrorMessage(bindingResult));
        }

        ReportOutputDto createdReport = reportService.createReport(reportInputDto);


        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReportOutputDto>> getAllReports() {
        List<ReportOutputDto> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReportOutputDto> getReportById(@PathVariable Long id) {
        ReportOutputDto report = reportService.getReportById(id);
        return ResponseEntity.ok(report);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        boolean isDeleted = reportService.deleteReport(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}


