package com.example.platenwinkel.dtos.input;

import com.example.platenwinkel.enumeration.DeliveryStatus;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Map;


public class OrderInputDto {

    @NotNull(message = "Username is required")
    public String username;
    @NotNull(message = "Order date is required")
    public LocalDate orderDate;
    public Double shippingCost;
    public int paymentStatus;// 0 = not paid, 1 = paid
    public DeliveryStatus deliveryStatus;
    @NotNull(message = "Shipping address is required")
    public String shippingAdress;
    public Map<Long, Integer> items; // Mapping product ID's to quantities

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getShippingAdress() {
        return shippingAdress;
    }

    public void setShippingAdress(String shippingAdress) {
        this.shippingAdress = shippingAdress;
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }
}
