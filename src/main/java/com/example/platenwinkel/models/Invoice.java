package com.example.platenwinkel.models;


import com.example.platenwinkel.enumeration.DeliveryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String invoiceNumber;

    @Column(nullable = false)
    @NotNull
    private LocalDate date;

    @Min(value = 0)
    @Max(value = 1)
    public int paymentStatus;// 0 = not paid, 1 = paid
    @Enumerated(EnumType.STRING)
    @NotNull
    public DeliveryStatus deliveryStatus;

    private LocalDate invoiceDate;
    @Column(nullable = false)
    private Double totalCost;

    @Column(nullable = false)
    private Double totalAmountExclVat;

    public static final double VAT_RATE = 0.21;


    // Default constructor
    public Invoice() {
    }

    public Invoice(Order order, String invoiceNumber, LocalDate date, int paymentStatus, DeliveryStatus deliveryStatus, LocalDate invoiceDate) {
        this.order = order;
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.paymentStatus = paymentStatus;
        this.deliveryStatus = deliveryStatus;
        this.invoiceDate = invoiceDate;
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
    public Double getTotalAmountExclVat() {
        double amountExclVat = order.getTotalOrderAmount() / (1 + VAT_RATE);
        return Math.round(amountExclVat * 100.0) / 100.0;
    }
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalAmountExclVat(Double totalAmountExclVat) {
        this.totalAmountExclVat = Math.round(totalAmountExclVat * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "orderId=" + order.getId() +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", totalAmountInclVat=" + order.getTotalOrderAmount() +
                ", totalAmountExclVat=" + getTotalAmountExclVat() +
                ", vatRate=" + (VAT_RATE * 100) + "%" +
                '}';
    }

}
