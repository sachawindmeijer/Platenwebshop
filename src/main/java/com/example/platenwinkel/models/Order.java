package com.example.platenwinkel.models;

import com.example.platenwinkel.enumeration.DeliveryStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Orders {

//    Beheer, analyse en filtering van bestellingen.
//Bijwerken van verzendkosten en bestelstatussen.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private User user;
    private Payment paymentMethod;
    private Boolean paid;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime buydate;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }



    public LocalDateTime getBuydate() {
        return buydate;
    }

    public void setBuydate(LocalDateTime buydate) {
        this.buydate = buydate;
    }
}
