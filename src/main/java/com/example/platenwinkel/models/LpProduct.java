package com.example.platenwinkel.models;


import jakarta.persistence.*;

@Entity
@Table(name = "LpProduct")
public class LpProduct {


    //    //  Een entiteit moet een primary key bevatten(id)

    //    // GeneratedValue betekend dat je deze waarde niet zelf hoeft in te vullen, dit doet Spring Boot voor jou bij het opslaan in de database.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String artist;
    private String titel;
    private Double price;
    private String description;

    private String genre;
    private boolean opvoorraad; // dit aanpassen in het klassen diagram
    private double priceInclVat;
    private double priceEclVat;//ook deze aanpassen

    //


    //
    public String getArtist() {
        return artist;
    }

    //
    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOpvoorraad() {
        return opvoorraad;
    }

    public void setOpvoorraad(boolean opvoorraad) {
        this.opvoorraad = opvoorraad;
    }

    public double getPriceInclVat() {
        return priceInclVat;
    }

    public void setPriceInclVat(double priceInclVat) {
        this.priceInclVat = priceInclVat;
    }

    public double getPriceEclVat() {
        return priceEclVat;
    }

    public void setPriceEclVat(double priceEclVat) {
        this.priceEclVat = priceEclVat;
    }


    public Long getId() {
        return id;
    }
}
//