package com.example.platenwinkel.dtos.mapper;

import com.example.platenwinkel.dtos.input.OrderInputDto;
import com.example.platenwinkel.dtos.output.OrderOutputDto;

import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderMapper {


    public static Order fromInputDToOrder(OrderInputDto orderInputDto, User user, Map<Long, LpProduct> productMap) {
        Order order = new Order();
        order.setUser(user);

        order.setOrderDate(orderInputDto.getOrderDate());
        order.setPaymentStatus(orderInputDto.getPaymentStatus());
        order.setDeliveryStatus(orderInputDto.getDeliveryStatus());
        order.setShippingAdress(orderInputDto.getShippingAdress());


        Map<LpProduct, Integer> items = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : orderInputDto.getItems().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            LpProduct product = productMap.get(productId);

            if (product != null) {
                items.put(product, quantity);
            }
        }

        order.setItems(items);
        order.calculateAndSetShippingCost();

        return order;
    }


    public static OrderOutputDto fromOrderToOutputDto(Order order) {

        OrderOutputDto orderOutputDto = new OrderOutputDto();


            orderOutputDto.setId(order.getId());
            orderOutputDto.setUsername(order.getUser().getUsername());
            orderOutputDto.setOrderDate(order.getOrderDate());
            orderOutputDto.setShippingCost(order.getShippingCost());
            orderOutputDto.setPaymentStatus(order.getPaymentStatus());
            orderOutputDto.setDeliveryStatus(order.getDeliveryStatus());
            orderOutputDto.setShippingAdress(order.getShippingAdress());
            orderOutputDto.setItems(order.getItems().entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().getId(), Map.Entry::getValue)));
            orderOutputDto.setTotalCost(order.getTotalCost());
            return orderOutputDto;
        }
}
