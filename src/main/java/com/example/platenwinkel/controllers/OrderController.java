package com.example.platenwinkel.controllers;

import com.example.platenwinkel.dtos.input.OrderInputDto;
import com.example.platenwinkel.dtos.mapper.OrderMapper;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.dtos.output.OrderOutputDto;
import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.helper.BindingResultHelper;
import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public ResponseEntity<List<OrderOutputDto>>getAllOrders() {
        List<OrderOutputDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {

        OrderOutputDto order = orderService.getOrderById(id);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public ResponseEntity<OrderOutputDto> createOrder( @RequestBody OrderInputDto orderInputDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException("Somthing went wrong, please check the following fields. " + BindingResultHelper.getErrorMessage(bindingResult));
        }
        String username = orderInputDto.getUsername();
        OrderOutputDto order= orderService.createOrder(orderInputDto, username);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + order.getId()).toUriString());


        return ResponseEntity.created(uri).body(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderOutputDto> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderInputDto orderInputDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException("Something went wrong, please check the following fields: " + BindingResultHelper.getErrorMessage(bindingResult));
        }
        OrderOutputDto updatedOrderOutputDto = orderService.updateOrder(id, orderInputDto);
        return ResponseEntity.ok(updatedOrderOutputDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
