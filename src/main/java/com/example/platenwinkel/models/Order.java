package com.example.platenwinkel.models;

import com.example.platenwinkel.enumeration.DeliveryStatus;
import jakarta.persistence.*;

import java.time.LocalDate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<LpProduct> products;  // Lijst van bestelde producten
    @Column(nullable = false)
    private LocalDate orderDate;
    private Double shippingCost;
    private int paymentStatus; // 0 = not paid, 1 = paid
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(nullable = false)
    private String shippingAdress;

    @ElementCollection // Geeft aan dat de items-collectie geen aparte entiteit is, maar onderdeel van de Order-entiteit.
    @CollectionTable(
            name = "order_items", // De naam van de tabel waarin de collectie wordt opgeslagen.
            joinColumns = @JoinColumn(name = "order_id") // De kolom die de relatie met de Order-entiteit aangeeft.
    )
    @MapKeyJoinColumn(name = "lpproduct_id") // Geeft aan dat de sleutel in de Map een verwijzing is naar een LpProduct.
    @Column(name = "quantity") // Geeft aan dat de waarde van de Map (Integer) als "quantity" wordt opgeslagen.
    private Map<LpProduct, Integer> items = new HashMap<>();

    private static final double FREE_SHIPPING_THRESHOLD = 50.00;
    private static final double STANDARD_SHIPPING_COST = 6.85;

    public Order() {
    }

    public Order(User user, LocalDate orderDate, int paymentStatus, DeliveryStatus deliveryStatus, String shippingAdress, Map<LpProduct, Integer> items) {
        this.user = user;
        this.orderDate = orderDate;
        this.paymentStatus = paymentStatus;
        this.deliveryStatus = deliveryStatus;
        this.shippingAdress = shippingAdress;
        this.items = items;
    }

    public double getTotalOrderAmount() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPriceInclVat() * entry.getValue())
                .sum();
    }

    public void calculateAndSetShippingCost() {
        double totalAmount = getTotalOrderAmount();
        this.shippingCost = totalAmount > FREE_SHIPPING_THRESHOLD ? 0.00 : STANDARD_SHIPPING_COST;
    }

    public double getTotalCost() {
        return getTotalOrderAmount() + (shippingCost != null ? shippingCost : 0.00);
    }

    // Getters and setters
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


    @Override
    public String toString() {
        return "Order{" +
                "customer=" + user +
                ", items=" + items +
                ", orderDate=" + orderDate +
                ", paymentStatus=" + (paymentStatus == 1 ? "Paid" : "Not Paid") +
                '}';
    }


}