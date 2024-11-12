package com.example.platenwinkel.models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @Column(nullable = false, unique = true)
    private String invoiceNumber;
    @Column(nullable = false)
    private double VAT;

    @Column(nullable = false)
    private LocalDate date;

    private LocalDate invoiceDate;
    private double amountExclVat;
    private double vatAmount;
    private double amountInclVat;


    // Default constructor
    public Invoice(){}

    public Invoice(Order order, String invoiceNumber, double VAT) {
        this.order = order;
        this.invoiceNumber = invoiceNumber;
        this.VAT = VAT;
        this.invoiceDate = LocalDate.now();
        calculateAmounts();
    }
    public void calculateAmounts() {
        this.amountExclVat = order.getTotalOrderAmount();
        this.vatAmount = amountExclVat * (VAT / 100);
        this.amountInclVat = amountExclVat + vatAmount;
    }
    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getAmountExclVat() {
        return amountExclVat;
    }

    public void setAmountExclVat(double amountExclVat) {
        this.amountExclVat = amountExclVat;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public double getAmountInclVat() {
        return amountInclVat;
    }

    public void setAmountInclVat(double amountInclVat) {
        this.amountInclVat = amountInclVat;
    }


    @Override
    public String toString() {
        return "Invoice{" +
                "orderId=" + order.getId() +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", VAT=" + VAT +
                ", amountInclVat=" + amountInclVat +
                '}';
    }

}
