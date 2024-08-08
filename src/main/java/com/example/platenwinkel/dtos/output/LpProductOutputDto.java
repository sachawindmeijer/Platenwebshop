package com.example.platenwinkel.dtos.output;

import com.example.platenwinkel.enumeration.Genre;

public class LpProductOutputDto {
    private Long id;
    public String artist;
    public String album;
    public String description;
    public Genre genre;
    public int inStock; // dit aanpassen in het klassen diagram

    public double priceInclVat;

    public double priceEclVat;//ook deze aanpassen

//    private Integer sellprice; // we willen niet dat de klant de sell price kan zien

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
}
