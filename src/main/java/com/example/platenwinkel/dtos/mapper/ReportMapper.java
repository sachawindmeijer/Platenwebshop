package com.example.platenwinkel.dtos.mapper;

import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.output.ReportOutputDto;

import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Report;

public class ReportMapper {

    public static Report fromInputDtoToMode(ReportInputDto reportInputDto, LpProduct lpProduct) {
        if (reportInputDto == null) {
            return null;
        }
        return new Report(
                lpProduct,
                reportInputDto.getVoorraadAantal(),
                reportInputDto.getVerkochtAantal(),
                reportInputDto.getRapportDatum()
        );
    }

    public static ReportOutputDto fromModelToOutputDto(Report report) {
        if (report == null) {
            return null;
        }

        ReportOutputDto reportOutputDto = new ReportOutputDto();
        reportOutputDto.setId(report.getId());

        // Set productId from LpProduct's id
        reportOutputDto.setProductId(report.getLpProduct().getId());

        // Optional: Set the product name if you want
        reportOutputDto.setArtist(report.getLpProduct().getArtist());
        reportOutputDto.setAlbum(report.getLpProduct().getAlbum());

        reportOutputDto.setVoorraadAantal(report.getVoorraadAantal());
        reportOutputDto.setVerkochtAantal(report.getVerkochtAantal());
        reportOutputDto.setRapportDatum(report.getRapportDatum());

        return reportOutputDto;
    }
}
