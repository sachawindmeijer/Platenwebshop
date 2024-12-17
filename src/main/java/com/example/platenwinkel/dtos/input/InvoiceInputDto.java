package com.example.platenwinkel.dtos.input;

import com.example.platenwinkel.enumeration.DeliveryStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class InvoiceInputDto {
    @NotBlank
    private String invoiceNumber;

    @Min(value = 0)
    @Max(value = 1)
    public int paymentStatus;
    @NotNull
    public DeliveryStatus deliveryStatus;


    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }
}
