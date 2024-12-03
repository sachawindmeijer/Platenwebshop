package com.example.platenwinkel.dtos.output;

import com.example.platenwinkel.enumeration.DeliveryStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class InvoiceOutputDto {
    private Long id;
    private String invoiceNumber;
    private Long orderId;
    private LocalDate invoiceDate;
    public int paymentStatus;// 0 = not paid, 1 = paid
    private Double totalAmountExclVat;  // Totaal bedrag exclusief btw
    private Double totalCost;
    public DeliveryStatus deliveryStatus;


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

    public Double getTotalAmountExclVat() {
        return totalAmountExclVat;
    }

    public void setTotalAmountExclVat(Double totalAmountExclVat) {
        this.totalAmountExclVat = totalAmountExclVat;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
