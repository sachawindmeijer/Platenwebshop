package com.example.platenwinkel.dtos.output;

import java.time.LocalDate;
import java.util.List;

public class InvoiceOutputDto {
    private Long id;
    private String invoiceNumber;
    private Long orderId;
    private LocalDate invoiceDate;
    private Double VAT;
    private Double amountExclVat;
    private Double vatAmount;
    private Double amountInclVat;

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

    public Double getVAT() {
        return VAT;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    public Double getAmountExclVat() {
        return amountExclVat;
    }

    public void setAmountExclVat(Double amountExclVat) {
        this.amountExclVat = amountExclVat;
    }

    public Double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Double getAmountInclVat() {
        return amountInclVat;
    }

    public void setAmountInclVat(Double amountInclVat) {
        this.amountInclVat = amountInclVat;
    }
}
