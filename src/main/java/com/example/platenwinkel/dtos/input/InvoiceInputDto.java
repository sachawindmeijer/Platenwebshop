package com.example.platenwinkel.dtos.input;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class InvoiceInputDto {

    private String invoiceNumber; // Uniek factuurnummer (door gebruiker ingevoerd)
    private double VAT; // BTW-percentage



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
}
