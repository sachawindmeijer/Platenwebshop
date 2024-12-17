package com.example.platenwinkel.dtos.input;

import com.example.platenwinkel.enumeration.Genre;
import jakarta.validation.constraints.*;


public class LpProductInputDto {


        @NotBlank
        public String artist;

        @NotBlank
        public String album;

        @Size(max = 500)
        public String description;
        public Genre genre;

        @Min(value = 0)
        public int inStock;

         @NotNull
    @Positive
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


    public Double getPriceExclVat() {
        return priceExclVat;
    }

    public void setPriceExclVat(Double priceExclVat) {
        this.priceExclVat = priceExclVat;
    }
}
