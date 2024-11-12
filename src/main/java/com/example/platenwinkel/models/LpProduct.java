package com.example.platenwinkel.models;

import com.example.platenwinkel.enumeration.Genre;
import com.example.platenwinkel.helper.PriceCalculator;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Lp_Product")
public class LpProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String artist;
    private String album;
    private String description;
    private LocalDate releaseDate;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private int inStock;




    private double priceInclVat;
    private double priceEclVat;

    public LpProduct(Long id, String artist, String album, String description, Genre genre, int inStock, double priceEclVat) {
        this.id = id;
        this.artist = artist;
        this.album = album;
        this.description = description;
        this.genre = genre;
        this.inStock = inStock;
        this.priceEclVat = priceEclVat;
    }
    public LpProduct() {

    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album= album;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public double getPriceInclVat() {
        return priceInclVat;
    }

    public void setPriceInclVat(double priceInclVat) {
        this.priceInclVat = PriceCalculator.calculatePriceInclVat(priceEclVat);
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
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }
}
