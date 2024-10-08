package com.example.platenwinkel.dtos.input;

import java.time.LocalDateTime;

public class ReportInputDto {
public Long productId;
    public int voorraadAantal;
    public int verkochtAantal;
    public LocalDateTime rapportDatum;

    public int getVoorraadAantal() {
        return voorraadAantal;
    }

    public int getVerkochtAantal() {
        return verkochtAantal;
    }

    public LocalDateTime getRapportDatum() {
        return rapportDatum;
    }

    public void setVoorraadAantal(int voorraadAantal) {
        this.voorraadAantal = voorraadAantal;
    }

    public void setVerkochtAantal(int verkochtAantal) {
        this.verkochtAantal = verkochtAantal;
    }

    public void setRapportDatum(LocalDateTime rapportDatum) {
        this.rapportDatum = rapportDatum;
    }
}
