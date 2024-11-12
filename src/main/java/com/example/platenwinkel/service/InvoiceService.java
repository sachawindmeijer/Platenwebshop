package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.InvoiceInputDto;
import com.example.platenwinkel.dtos.mapper.InvoiceMapper;
import com.example.platenwinkel.dtos.output.InvoiceOutputDto;

import com.example.platenwinkel.models.Invoice;
import com.example.platenwinkel.models.Order;


import com.example.platenwinkel.models.User;
import com.example.platenwinkel.repositories.InvoiceRepository;
import com.example.platenwinkel.repositories.OrderRepository;
import com.example.platenwinkel.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


import java.util.stream.Collectors;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }


    public List<InvoiceOutputDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .map(InvoiceMapper::fromInvoiceToOutputDto)
                .collect(Collectors.toList());
    }

    public InvoiceOutputDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
        return InvoiceMapper.fromInvoiceToOutputDto(invoice);
    }

    public InvoiceOutputDto createInvoice(InvoiceInputDto inputDto, String username, Long orderId) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (invoiceRepository.existsByOrderId(orderId)) {
            throw new RuntimeException("An invoice already exists for this order.");
        }
        Invoice invoice = InvoiceMapper.fromInputDtoToModel(inputDto, user, order);
        invoice.setDate(LocalDate.now());
        Invoice savedInvoice = invoiceRepository.save(invoice);

        return InvoiceMapper.fromInvoiceToOutputDto(savedInvoice);
    }


    public InvoiceOutputDto updateInvoice(Long id, InvoiceInputDto inputDto) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        existingInvoice.setInvoiceNumber(inputDto.getInvoiceNumber());
        existingInvoice.setVAT(inputDto.getVAT());
        existingInvoice.setInvoiceDate(LocalDate.now()); // or use invoiceInputDto.getInvoiceDate() if available
        existingInvoice.calculateAmounts(); // Recalculate amounts based on updated VAT or other fields

        Invoice updatedInvoice = invoiceRepository.save(existingInvoice);
        return InvoiceMapper.fromInvoiceToOutputDto(updatedInvoice);

    }
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new IllegalArgumentException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }

}