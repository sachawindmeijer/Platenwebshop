package com.example.platenwinkel.dtos.mapper;

import com.example.platenwinkel.dtos.input.ReportInputDto;
import com.example.platenwinkel.dtos.output.ReportOutputDto;

import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReportMapper {

    public static Report fromInputDtoToMode(ReportInputDto inputDto, List<LpProduct> topSellingProducts, List<LpProduct> lowSellingProducts, Double totalRevenue) {
        Report report = new Report(
                topSellingProducts,
                lowSellingProducts,
                totalRevenue
        );
        report.setComment(inputDto.getComment());
        return report;
    }

    public static ReportOutputDto fromModelToOutputDto(Report report) {
        ReportOutputDto outputDto = new ReportOutputDto(
                report.getTopSellingProducts(),
                report.getLowSellingProducts(),
                report.getTotalRevenue()
        );

        outputDto.setId(report.getId());
        outputDto.setRapportDatum(LocalDate.from(LocalDateTime.now()));
        outputDto.setComment(report.getComment());

        return outputDto;
    }
}
