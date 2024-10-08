package com.example.platenwinkel.repositories;

import com.example.platenwinkel.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository  extends JpaRepository<Invoice, Long> {
}
