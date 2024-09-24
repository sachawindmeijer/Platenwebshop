package com.example.platenwinkel.dtos.output;

import java.time.LocalDateTime;

public class ReportOutputDto {
    private Long id;
    private Long productId;
    private String artist;
    public String album;
    private int voorraadAantal;
    private int verkochtAantal;
    private LocalDateTime rapportDatum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        this.album = album;
    }

    public int getVoorraadAantal() {
        return voorraadAantal;
    }

    public void setVoorraadAantal(int voorraadAantal) {
        this.voorraadAantal = voorraadAantal;
    }

    public int getVerkochtAantal() {
        return verkochtAantal;
    }

    public void setVerkochtAantal(int verkochtAantal) {
        this.verkochtAantal = verkochtAantal;
    }

    public LocalDateTime getRapportDatum() {
        return rapportDatum;
    }

    public void setRapportDatum(LocalDateTime rapportDatum) {
        this.rapportDatum = rapportDatum;
    }
}
