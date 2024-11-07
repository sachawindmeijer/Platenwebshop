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

    // ---------------Dit sit in de mapper fromInputDtoToModel
    public static Order fromInputDToOrder(OrderInputDto orderInputDto, User user, Map<Long, LpProduct> productMap) {
        Order order = new Order();
        order.setUser(user);
        // Zet de velden van de OrderInputDto
        order.setOrderDate(orderInputDto.getOrderDate());
        order.setPaymentStatus(orderInputDto.getPaymentStatus());
        order.setDeliveryStatus(orderInputDto.getDeliveryStatus());
        order.setShippingAdress(orderInputDto.getShippingAdress());

        // Verwerk de items en zet ze om in de juiste vorm
        Map<LpProduct, Integer> items = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : orderInputDto.getItems().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            LpProduct product = productMap.get(productId); // Zoek het product op basis van ID

            if (product != null) { // Controleer of het product bestaat
                items.put(product, quantity);
            }
        }

        order.setItems(items); // Zet de items in de order
        order.calculateAndSetShippingCost(); // Bereken en zet verzendkosten

        return order;
    }


    public static OrderOutputDto fromOrderToOutputDto(Order order) {

        OrderOutputDto orderOutputDto = new OrderOutputDto();

            // Zet de velden van de Order entiteit
            orderOutputDto.setId(order.getId());
            orderOutputDto.setUsername(order.getUser().getUsername()); // Haal gebruikersnaam uit de User entiteit
            orderOutputDto.setOrderDate(order.getOrderDate());
            orderOutputDto.setShippingCost(order.getShippingCost());
            orderOutputDto.setPaymentStatus(order.getPaymentStatus());
            orderOutputDto.setDeliveryStatus(order.getDeliveryStatus());
            orderOutputDto.setShippingAdress(order.getShippingAdress());
            orderOutputDto.setItems(order.getItems().entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().getId(), Map.Entry::getValue))); // Zet producten om naar ID's
            orderOutputDto.setTotalCost(order.getTotalCost()); // Zet de totale kosten

            return orderOutputDto;
        }
}
