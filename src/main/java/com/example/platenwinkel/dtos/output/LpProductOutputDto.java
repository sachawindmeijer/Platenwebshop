package com.example.platenwinkel.dtos.output;

import com.example.platenwinkel.enumeration.Genre;

public class LpProductOutputDto {
    private Long id;

    public String artist;
    public String album;
    public String description;
    public Genre genre;
    public int inStock;

    public Double priceInclVat;

    public Double priceExclVat;

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

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPriceInclVat() {
        return priceInclVat;
    }

    public void setPriceInclVat(Double priceInclVat) {
        this.priceInclVat = priceInclVat;
    }

    public Double getPriceExclVat() {
        return priceExclVat;
    }

    public void setPriceExclVat(Double priceExclVat) {
        this.priceExclVat = priceExclVat;
    }
}
