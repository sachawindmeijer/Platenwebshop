package com.example.platenwinkel.repositories;

import com.example.platenwinkel.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {




}
