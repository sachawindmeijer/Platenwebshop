package com.example.platenwinkel.controllers;

import com.example.platenwinkel.models.Report;
import com.example.platenwinkel.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/report")
public class ReportController {

    private final  ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<Report> getAllRapporten() {
        return reportService.getAllRapporten();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getRapportById(@PathVariable Long id) {
        Report rapport = reportService.getRapportById(id);
        if (rapport != null) {
            return ResponseEntity.ok(rapport);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Report createRapport(@RequestBody Report rapport) {
        return reportService.createRapport(rapport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRapport(@PathVariable Long id) {
        reportService.deleteRapport(id);
        return ResponseEntity.noContent().build();
    }
}


