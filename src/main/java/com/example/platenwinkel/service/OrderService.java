package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.OrderInputDto;
import com.example.platenwinkel.dtos.mapper.LpProductMapper;
import com.example.platenwinkel.dtos.mapper.OrderMapper;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.dtos.output.OrderOutputDto;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.models.User;
import com.example.platenwinkel.repositories.LpProductRepository;
import com.example.platenwinkel.repositories.OrderRepository;
import com.example.platenwinkel.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final LpProductRepository lpProductRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, LpProductRepository lpProductRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.lpProductRepository = lpProductRepository;
    }

    public List<OrderOutputDto> getAllOrders() {
        List<Order> orderlist = orderRepository.findAll();
        List<OrderOutputDto> oderDtoList = new ArrayList<>();

        for (Order orderl : orderlist) {
            OrderOutputDto dto = OrderMapper.fromOrderToOutputDto(orderl);
            oderDtoList.add(dto);
        }
        return oderDtoList;
    }

    public OrderOutputDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order not found with ID: " + id));

        return OrderMapper.fromOrderToOutputDto(order);
    }


    public OrderOutputDto createOrder(OrderInputDto orderInputDto, String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found with ID: " + username));

        Map<Long, LpProduct> productMap = new HashMap<>();
        for (Long productId : orderInputDto.getItems().keySet()) {
            LpProduct product = lpProductRepository.findById(productId)
                    .orElseThrow(() -> new RecordNotFoundException("Product not found with ID: " + productId));
            productMap.put(productId, product);
        }

        Order order = OrderMapper.fromInputDToOrder(orderInputDto, user, productMap);

        Order savedOrder = orderRepository.save(order);
        order.setOrderDate(LocalDate.now());
        return OrderMapper.fromOrderToOutputDto(savedOrder);
    }


    public OrderOutputDto updateOrder(Long id, OrderInputDto orderInputDto) {

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order not found with ID: " + id));


//        existingOrder.setPaymentStatus(orderInputDto.getPaymentStatus());
//        existingOrder.setDeliveryStatus(orderInputDto.getDeliveryStatus());
        existingOrder.setShippingAdress(orderInputDto.getShippingAdress());

        Map<Long, Integer> itemsDto = orderInputDto.getItems();
        Map<LpProduct, Integer> items = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : itemsDto.entrySet()) {
            LpProduct product = lpProductRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RecordNotFoundException("Product not found with ID: " + entry.getKey()));
            items.put(product, entry.getValue());
        }
        existingOrder.setItems(items);

        existingOrder.calculateAndSetShippingCost();


        Order updatedOrder = orderRepository.save(existingOrder);

        return OrderMapper.fromOrderToOutputDto(updatedOrder);
    }

    public void deleteOrder(@RequestBody Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RecordNotFoundException("No order found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}
