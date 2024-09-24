package com.example.platenwinkel.dtos.input;

import com.example.platenwinkel.enumeration.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;



public class LpProductInputDto {
// dit vul je in in Postman
//public Long id;
    @NotBlank
    public String artist;
    public String album;
    public String description;
    public Genre genre;
    public int inStock; // dit aanpassen in het klassen diagram


    public double  PriceInclVat;


    public double priceEclVat;//ook deze aanpassen



// public double sellPrice; dit niet in de input dto zetten anders kan de klant het zien

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

    public double getPriceInclVat() {
        return PriceInclVat;
    }

    public void setPriceInclVat(double priceInclVat) {
        PriceInclVat = priceInclVat;
    }

    public double getPriceEclVat() {
        return priceEclVat;
    }

    public void setPriceEclVat(double priceEclVat) {
        this.priceEclVat = priceEclVat;
    }
}
