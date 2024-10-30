package com.example.platenwinkel.dtos.output;

import java.time.LocalDate;
import java.util.List;

public class InvoiceOutputDto {
    private Long id;
    private String invoiceNumber;
    private Long orderId;
    private LocalDate invoiceDate;
    private double VAT;
    private double amountExclVat;
    private double vatAmount;
    private double amountInclVat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
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
}
