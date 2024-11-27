package com.example.platenwinkel.dtos.input;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class InvoiceInputDto {

    private String invoiceNumber;
    private Double VAT;



    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getVAT() {
        return VAT;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }
}
