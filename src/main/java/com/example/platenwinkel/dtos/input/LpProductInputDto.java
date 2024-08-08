package com.example.platenwinkel.dtos.input;

import com.example.platenwinkel.enumeration.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;



public class LpProductInputDto {
// dit vul je in in Postman
private Long id;
    @NotBlank
    public String artist;
    public String album;
    public String description;
    public Genre genre;
    public int inStock; // dit aanpassen in het klassen diagram

    @Positive
    public double  PriceInclVat;

    @Positive
    public double priceEclVat;//ook deze aanpassen



// public double sellPrice; dit niet in de input dto zetten anders kan de klant het zien

    // de getter and setter gaaan naar de mapper
//    public String getArtist() {
//        return artist;
//    }
//
//    public void setArtist(String artist) {
//        this.artist = artist;
//    }
//
//    public String getTitel() {
//        return titel;
//    }
//
//    public void setTitel(String titel) {
//        this.titel = titel;
//    }
//
//
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getGenre() {
//        return genre;
//    }
//
//    public void setGenre(String genre) {
//        this.genre = genre;
//    }
//
//    public LocalDate getOriginalStock() {
//        return originalStock;
//    }
//
//    public void setOriginalStock(LocalDate originalStock) {
//        this.originalStock = originalStock;
//    }
//
//    public double getPriceEclVat() {
//        return priceEclVat;
//    }
//
//    public void setPriceEclVat(double priceEclVat) {
//        this.priceEclVat = priceEclVat;
//    }
//
//    public double getPriceInclVat() {
//        return PriceInclVat;
//    }
//
//    public void setPriceInclVat(double priceInclVat) {
//        PriceInclVat = priceInclVat;
//    }
}
