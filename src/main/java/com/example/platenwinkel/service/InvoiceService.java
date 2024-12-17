package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.InvoiceInputDto;
import com.example.platenwinkel.dtos.mapper.InvoiceMapper;
import com.example.platenwinkel.dtos.output.InvoiceOutputDto;

import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.models.Invoice;
import com.example.platenwinkel.models.Order;


import com.example.platenwinkel.models.User;
import com.example.platenwinkel.repositories.InvoiceRepository;
import com.example.platenwinkel.repositories.OrderRepository;
import com.example.platenwinkel.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


import java.util.stream.Collectors;

import static com.example.platenwinkel.models.Invoice.VAT_RATE;

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
                .orElseThrow(() -> new RecordNotFoundException("Invoice not found with ID " + id));
        return InvoiceMapper.fromInvoiceToOutputDto(invoice);
    }

    public InvoiceOutputDto createInvoice(InvoiceInputDto inputDto, String username, Long orderId) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found " + username));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RecordNotFoundException("Order not found " + orderId));

        if (invoiceRepository.existsByOrderId(orderId)) {
            throw new InvalidInputException("An invoice already exists for this order ID " + orderId);
        }
        Invoice invoice = InvoiceMapper.fromInputDtoToModel(inputDto, user, order);
        invoice.setOrder(order);
        invoice.setDate(LocalDate.now());
        double totalAmountExclVat = order.getItems().entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPriceExclVat() * entry.getValue())
                .sum();
        double totalCost = totalAmountExclVat * (1 + VAT_RATE) + order.getShippingCost();
        invoice.setTotalAmountExclVat(totalAmountExclVat);
        invoice.setTotalCost(totalCost);
        Invoice savedInvoice = invoiceRepository.save(invoice);

        return InvoiceMapper.fromInvoiceToOutputDto(savedInvoice);
    }


    public InvoiceOutputDto updateInvoice(Long id, InvoiceInputDto inputDto) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Invoice not found with ID " + id));

        existingInvoice.setInvoiceNumber(inputDto.getInvoiceNumber());
        existingInvoice.setPaymentStatus(inputDto.getPaymentStatus());
        existingInvoice.setDeliveryStatus(inputDto.getDeliveryStatus());
        existingInvoice.setInvoiceDate(LocalDate.now());

        Invoice updatedInvoice = invoiceRepository.save(existingInvoice);
        return InvoiceMapper.fromInvoiceToOutputDto(updatedInvoice);

    }
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new RecordNotFoundException ("Invoice not found with ID " + id);
        }
        invoiceRepository.deleteById(id);
    }

}