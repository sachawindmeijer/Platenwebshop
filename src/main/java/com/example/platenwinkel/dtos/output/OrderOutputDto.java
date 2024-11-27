package com.example.platenwinkel.dtos.output;

import com.example.platenwinkel.enumeration.DeliveryStatus;
import com.example.platenwinkel.models.Order;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderOutputDto {

    private Long id;
    private String username;
    private LocalDate orderDate;
    private Double shippingCost;
    private int paymentStatus;
    private DeliveryStatus deliveryStatus;
    private String shippingAdress;
    private Map<Long, Integer> items;
    private Double totalCost;

    public OrderOutputDto() {}

    public OrderOutputDto(Long id, String username, LocalDate orderDate, Double shippingCost, int paymentStatus, DeliveryStatus deliveryStatus, String shippingAdress, Map<Long, Integer> items, double totalCost) {
        this.id = id;
        this.username = username;
        this.orderDate = orderDate;
        this.shippingCost = shippingCost;
        this.paymentStatus = paymentStatus;
        this.deliveryStatus = deliveryStatus;
        this.shippingAdress = shippingAdress;
        this.items = items;
        this.totalCost = totalCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }
}
