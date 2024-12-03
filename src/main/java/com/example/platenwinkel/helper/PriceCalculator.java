package com.example.platenwinkel.helper;

public class PriceCalculator {
    public static Double calculatePriceInclVat(Double priceExclVat) {
        if (priceExclVat == null || priceExclVat <= 0) {
            throw new IllegalArgumentException("Price excluding VAT must be greater than 0");
        }
        return priceExclVat * 1.21;
    }
}
