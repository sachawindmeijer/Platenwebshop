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
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            return OrderMapper.fromOrderToOutputDto(order);
        } else {
            throw new RecordNotFoundException("geen lpproduct gevonden");
        }
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
        // Map the input DTO to an Order entity
        Order order = OrderMapper.fromInputDToOrder(orderInputDto, user, productMap);

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Map the saved order to an output DTO
        return OrderMapper.fromOrderToOutputDto(savedOrder);
    }

    // Update an existing order
    public OrderOutputDto updateOrder(Long id, OrderInputDto orderInputDto) {
        // Find the existing order
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order not found with ID: " + id));

        // Map the updated details from the input DTO
        existingOrder.setOrderDate(orderInputDto.getOrderDate());
        existingOrder.setPaymentStatus(orderInputDto.getPaymentStatus());
        existingOrder.setDeliveryStatus(orderInputDto.getDeliveryStatus());
        existingOrder.setShippingAdress(orderInputDto.getShippingAdress());

        // Map updated product items
        Map<Long, Integer> itemsDto = orderInputDto.getItems();
        Map<LpProduct, Integer> items = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : itemsDto.entrySet()) {
            LpProduct product = lpProductRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RecordNotFoundException("Product not found with ID: " + entry.getKey()));
            items.put(product, entry.getValue());
        }
        existingOrder.setItems(items);

        // Calculate new shipping cost
        existingOrder.calculateAndSetShippingCost();

        // Save the updated order
        Order updatedOrder = orderRepository.save(existingOrder);

        // Map the updated order to an output DTO
        return OrderMapper.fromOrderToOutputDto(updatedOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
