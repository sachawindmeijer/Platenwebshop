package com.example.platenwinkel.service;

import com.example.platenwinkel.models.Report;
import com.example.platenwinkel.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {


    private final ReportRepository rapportRepository;

    public ReportService(ReportRepository rapportRepository) {
        this.rapportRepository = rapportRepository;
    }

    public List<Report> getAllRapporten() {
        return rapportRepository.findAll();
    }

    public Report getRapportById(Long id) {
        return rapportRepository.findById(id).orElse(null);
    }

    public Report createRapport(Report rapport) {
        return rapportRepository.save(rapport);
    }

    public void deleteRapport(Long id) {
        rapportRepository.deleteById(id);
    }
}

