package com.example.platenwinkel.dtos.input;

import com.example.platenwinkel.enumeration.DeliveryStatus;
import com.example.platenwinkel.models.Customer;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Order;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderInputDto {

    public Long customerId;
    public LocalDate orderDate;
    public Double shippingCost;
    public int paymentStatus;
    public DeliveryStatus deliveryStatus;
    public String shippingAdress;
    public Map<Long, Integer> items; // Mapping product ID's to quantities

    public Order toOrder() {
        Customer customer = new Customer();
        customer.setId(customerId); // Assuming a valid customer ID is provided

        Map<LpProduct, Integer> productItems = items.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> {
                            LpProduct product = new LpProduct();
                            product.setId(entry.getKey());
                            return product;
                        },
                        Map.Entry::getValue
                ));

        return new Order(customer, orderDate, shippingCost, paymentStatus, deliveryStatus, shippingAdress, productItems);
    }

}
