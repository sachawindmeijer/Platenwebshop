package com.example.platenwinkel.models;

import jakarta.persistence.*;
//
//@Entity
//public class Customer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
//    private User user; // Reference to the User class
//
//    @Column(nullable = false)
//    private String firstName;
//
//    @Column(nullable = false)
//    private String lastName;
//
//    @Column(nullable = false)
//    private String address;
//
//    @Column(nullable = false)
//    private String city;
//
//    @Column(nullable = false)
//    private String country;
//
//
//
//
//    public Customer() {}
//
//    public Customer(User user, String firstName, String lastName, String address, String city, String country) {
//        this.user = user;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.address = address;
//        this.city = city;
//        this.country = country;
//
//    }
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//
//}