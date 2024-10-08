package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.InvoiceInputDto;
import com.example.platenwinkel.dtos.mapper.InvoiceMapper;
import com.example.platenwinkel.dtos.output.InvoiceOutputDto;
import com.example.platenwinkel.models.Customer;
import com.example.platenwinkel.models.Invoice;
import com.example.platenwinkel.models.Order;

import com.example.platenwinkel.repositories.CustomerRepository;
import com.example.platenwinkel.repositories.InvoiceRepository;
import com.example.platenwinkel.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
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

    public InvoiceOutputDto createInvoice(InvoiceInputDto invoiceInputDto) {
        // Haal de klant op
        Customer customer = customerRepository.findById(invoiceInputDto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Haal de bijbehorende orders op
        List<Order> orders = invoiceInputDto.getOrderIds().stream()
                .map(orderId -> orderRepository.findById(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId)))
                .collect(Collectors.toList());

        // Maak de Invoice aan en sla deze op
        Invoice invoice = InvoiceMapper.fromInputDtoToModel(invoiceInputDto, customer, orders);
        invoice = invoiceRepository.save(invoice);

        // Retourneer de output DTO
        return InvoiceMapper.fromInvoiceToOutputDto(invoice);
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public InvoiceOutputDto updateInvoice(Long id, InvoiceInputDto inputDto) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        // Haal de klant en orders opnieuw op om te updaten
        Customer customer = customerRepository.findById(inputDto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        List<Order> orders = inputDto.getOrderIds().stream()
                .map(orderId -> orderRepository.findById(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId)))
                .collect(Collectors.toList());

        // Update de bestaande factuur
        existingInvoice.setInvoiceNumber(inputDto.getInvoiceNumber());
        existingInvoice.setVAT(inputDto.getVAT());
        existingInvoice.setShippingCost(inputDto.getShippingCost());
        existingInvoice.setDate(inputDto.getDate());
        existingInvoice.setCustomer(customer);
        existingInvoice.setItems(orders);
        existingInvoice.setTotalAmount(inputDto.getTotalAmount());

        // Sla de updates op
        invoiceRepository.save(existingInvoice);

        return InvoiceMapper.fromInvoiceToOutputDto(existingInvoice);
    }
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new IllegalArgumentException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }

}