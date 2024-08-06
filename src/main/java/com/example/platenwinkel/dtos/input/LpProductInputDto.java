package com.example.platenwinkel.dtos.input;

import java.time.LocalDate;

public class LpProductInput {

    public String artist;
    public String titel;
    public Double price;
    public String description;
@Positive
    public String genre;
    public LocalDate originalStock; // dit aanpassen in het klassen diagram
    public double priceInclVat;
    public double priceEclVat;//ook deze aanpassen
}
