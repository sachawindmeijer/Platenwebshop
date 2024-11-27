package com.example.platenwinkel.helper;

public class PriceCalculator {
    public static Double calculatePriceInclVat(Double priceExclVat) {
        return priceExclVat * 1.21;
    }
}
