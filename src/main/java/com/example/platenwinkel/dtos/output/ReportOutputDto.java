package com.example.platenwinkel.dtos.output;

import com.example.platenwinkel.models.LpProduct;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReportOutputDto {
    private Long id;
    private List<LpProduct> topSellingProducts;
    private List<LpProduct> lowSellingProducts;
    private Double totalRevenue;
    private LocalDate rapportDatum;
    private String comment;

    public ReportOutputDto(List<LpProduct> topSellingProducts, List<LpProduct> lowSellingProducts, Double totalRevenue) {
        this.topSellingProducts = topSellingProducts;
        this.lowSellingProducts = lowSellingProducts;
        this.totalRevenue = totalRevenue;
    }

    public ReportOutputDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<LpProduct> getTopSellingProducts() {
        return topSellingProducts;
    }

    public void setTopSellingProducts(List<LpProduct> topSellingProducts) {
        this.topSellingProducts = topSellingProducts;
    }

    public List<LpProduct> getLowSellingProducts() {
        return lowSellingProducts;
    }

    public void setLowSellingProducts(List<LpProduct> lowSellingProducts) {
        this.lowSellingProducts = lowSellingProducts;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getRapportDatum() {
        return rapportDatum;
    }

    public void setRapportDatum(LocalDate rapportDatum) {
        this.rapportDatum = rapportDatum;
    }
}
