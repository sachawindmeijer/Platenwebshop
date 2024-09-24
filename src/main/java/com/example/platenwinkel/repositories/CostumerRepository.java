package com.example.platenwinkel.repositories;

import com.example.platenwinkel.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerRepository  extends JpaRepository<Customer, Long> {

}