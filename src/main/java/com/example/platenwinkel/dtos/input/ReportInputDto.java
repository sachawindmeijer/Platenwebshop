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
}
