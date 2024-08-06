package com.example.platenwinkel.models;

import jakarta.persistence.*;

//detailinvoice
@Entity
public class ShoppingCart {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        //        @ManyToOne
//        @JoinColumn(name = "invoice_id", nullable = false)
        private Invoice invoice;

//                @ManyToOne
//        @JoinColumn(name = "record_id", nullable = false)
        private LpProduct lpproduct;

                @Column(nullable = false)
        private Integer quantity;

                @Column(nullable = false)
        private Double price;

        // Default constructor
        public ShoppingCart() {
        }

        // Parameterized constructor
        public ShoppingCart(Invoice invoice, LpProduct lpproduct, Integer quantity, Double price) {
            this.invoice = invoice;
            this.lpproduct = lpproduct;
            this.quantity = quantity;
            this.price = price;
        }

        // Getters and setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Invoice getInvoice() {
            return invoice;
        }

        public void setInvoice(Invoice invoice) {
            this.invoice = invoice;
        }


        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

    public LpProduct getLpproduct() {
        return lpproduct;
    }

    public void setLpproduct(LpProduct lpproduct) {
        this.lpproduct = lpproduct;
    }

    public Double getprice() {
        return price;
    }

    public void setprice(Double totalprice) {
        this.price = totalprice;
    }
}
