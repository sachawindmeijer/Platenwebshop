package com.example.platenwinkel.controllers;

import com.example.platenwinkel.dtos.input.InvoiceInputDto;
import com.example.platenwinkel.dtos.output.InvoiceOutputDto;
import com.example.platenwinkel.models.Invoice;
import com.example.platenwinkel.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceOutputDto>> getAllInvoices() {
        List<InvoiceOutputDto> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceOutputDto> getInvoiceById(@PathVariable Long id) {
        InvoiceOutputDto invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<InvoiceOutputDto>createInvoice(@RequestBody InvoiceInputDto invoiceInputDto,  @RequestParam String username,
                                                         @RequestParam Long orderId) {
        InvoiceOutputDto createdInvoice = invoiceService.createInvoice(invoiceInputDto, username, orderId);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceOutputDto> updateInvoice(@PathVariable Long id, @RequestBody InvoiceInputDto inputDto) {
        InvoiceOutputDto updatedInvoice = invoiceService.updateInvoice(id, inputDto);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

}
