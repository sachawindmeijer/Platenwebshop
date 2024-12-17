package com.example.platenwinkel.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalTime.now;

@Entity
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<LpProduct> products;

    @Column
    private LocalDate orderDate;
    @PositiveOrZero
    private Double shippingCost;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(nullable = false)
    private String shippingAdress;

    @ElementCollection
    @CollectionTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_id")
    )

    @MapKeyJoinColumn(name = "lpproduct_id")
    @Column(name = "quantity")
    @NotEmpty
    private Map<LpProduct, Integer> items = new HashMap<>();

    private static final Double FREE_SHIPPING_THRESHOLD = 50.00;
    private static final Double STANDARD_SHIPPING_COST = 6.85;

    public Order() {
    }

    public Order(User user, LocalDate orderDate, String shippingAdress, Map<LpProduct, Integer> items) {
        this.user = user;
        this.orderDate = orderDate;

        this.shippingAdress = shippingAdress;
        this.items = items;
    }

    public Double getTotalOrderAmount() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPriceInclVat() * entry.getValue())
                .sum();
    }

    public void calculateAndSetShippingCost() {
        double totalItemCost = items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPriceInclVat() * entry.getValue())
                .sum();
        this.shippingCost = totalItemCost >= FREE_SHIPPING_THRESHOLD ? 0.0 : STANDARD_SHIPPING_COST;
    }

    public double calculateTotalCost() {
        double totalItemCost = items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPriceInclVat() * entry.getValue())
                .sum();
        return totalItemCost + (shippingCost != null ? shippingCost : 0.0);
    }

    public Double getTotalCost() {
        return getTotalOrderAmount() + (shippingCost != null ? shippingCost : 0.00);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItems(Map<LpProduct, Integer> items) {
        this.items = items;
    }

    public Map<LpProduct, Integer> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

//    public int getPaymentStatus() {
//        return paymentStatus;
//    }
//z
//    public void setPaymentStatus(int paymentStatus) {
//        this.paymentStatus = paymentStatus;
//    }




    public String getShippingAdress() {
        return shippingAdress;
    }

    public void setShippingAdress(String shippingAdress) {
        this.shippingAdress = shippingAdress;
    }


    @Override
    public String toString() {
        return "Order{" +
                "customer=" + user +
                ", items=" + items +
                ", orderDate=" + orderDate +

                '}';
    }


}
