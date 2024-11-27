package com.example.platenwinkel.dtos.input;

import com.example.platenwinkel.enumeration.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;



public class LpProductInputDto {


    @NotBlank
    public String artist;
    public String album;
    public String description;
    public Genre genre;
    public int inStock;

    public Double PriceInclVat;


    public Double priceEclVat;


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
        this.album = album;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public Double getPriceInclVat() {
        return PriceInclVat;
    }

    public void setPriceInclVat(Double priceInclVat) {
        PriceInclVat = priceInclVat;
    }

    public Double getPriceEclVat() {
        return priceEclVat;
    }

    public void setPriceEclVat(Double priceEclVat) {
        this.priceEclVat = priceEclVat;
    }
}
