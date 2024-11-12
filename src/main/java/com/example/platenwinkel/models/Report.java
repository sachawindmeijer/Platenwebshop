package com.example.platenwinkel.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Report {

        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String comment;
    private LocalDate rapportDatum;

    @ManyToMany
    @JoinTable(
            name = "report_top_selling_products",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "lpproduct_id")
    )
    private List<LpProduct> topSellingProducts;

    @ManyToMany
    @JoinTable(
            name = "report_low_selling_products",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "lp_product_id")
    )
    private List<LpProduct> lowSellingProducts;

    private Double totalRevenue;

    public Report() {
        this.rapportDatum = LocalDate.now();
    }

    public Report(List<LpProduct> topSellingProducts, List<LpProduct> lowSellingProducts, double totalRevenue) {
        this.rapportDatum = LocalDate.now();
        this.topSellingProducts = topSellingProducts;
        this.lowSellingProducts = lowSellingProducts;
        this.totalRevenue = totalRevenue;
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

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public LocalDate getrapportDatum() {
        return rapportDatum;
    }

    public void setrapportDatum(LocalDate rapportDatum) {
        this.rapportDatum = rapportDatum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
