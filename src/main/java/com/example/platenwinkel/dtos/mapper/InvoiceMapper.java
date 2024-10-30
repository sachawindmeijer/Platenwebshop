package com.example.platenwinkel.dtos.mapper;

import com.example.platenwinkel.dtos.input.InvoiceInputDto;

import com.example.platenwinkel.dtos.output.InvoiceOutputDto;

import com.example.platenwinkel.models.Invoice;

import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceMapper {
    public static Invoice fromInputDtoToModel(InvoiceInputDto invoiceInputDto, User user, Order orders) {
        Invoice invoice = new Invoice();
        invoice.setOrder(orders);
        invoice.setInvoiceNumber(invoiceInputDto.getInvoiceNumber());
        invoice.setVAT(invoiceInputDto.getVAT());
        invoice.setInvoiceDate(LocalDate.now());
        invoice.calculateAmounts(); // Bereken de bedragen op basis van het order en de BTW
        return invoice;

    }

    public static InvoiceOutputDto fromInvoiceToOutputDto(Invoice invoice) {
        InvoiceOutputDto dto = new InvoiceOutputDto();

        dto.setId(invoice.getId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setOrderId(invoice.getOrder().getId());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setVAT(invoice.getVAT());
        dto.setAmountExclVat(invoice.getAmountExclVat());
        dto.setVatAmount(invoice.getVatAmount());
        dto.setAmountInclVat(invoice.getAmountInclVat());
        return dto;
    }

}