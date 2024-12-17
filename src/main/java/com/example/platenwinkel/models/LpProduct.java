package com.example.platenwinkel.models;

import com.example.platenwinkel.enumeration.Genre;
import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.helper.PriceCalculator;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "Lp_Product")
public class LpProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String artist;
    @NotBlank
    private String album;
    @Size(max = 500)
    private String description;
    private LocalDate releaseDate;
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Min(value = 0)
    private int inStock;


    private Double priceInclVat;

    @NotNull
    @Positive
    private Double priceExclVat;

    public LpProduct(Long id, String artist, String album, String description, Genre genre, int inStock, Double priceExclVat) {
        this.id = id;
        this.artist = artist;
        this.album = album;
        this.description = description;
        this.genre = genre;
        this.inStock = inStock;
        this.priceExclVat = priceExclVat;
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

    public Double getPriceInclVat() {
        return priceInclVat;
    }

    public void setPriceInclVat() {
        if (this.priceExclVat != null) {
            this.priceInclVat = PriceCalculator.calculatePriceInclVat(this.priceExclVat);
        } else {
            throw new IllegalStateException("Price excluding VAT is not set.");
        }
    }

    public Double getPriceExclVat() {
        return priceExclVat;
    }

    public void setPriceExclVat(Double priceExclVat) {
        this.priceExclVat = priceExclVat;
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
