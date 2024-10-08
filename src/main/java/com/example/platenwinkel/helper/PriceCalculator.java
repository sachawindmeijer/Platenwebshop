package com.example.platenwinkel.helper;

public class PriceCalculator {
    public static double calculatePriceInclVat(double priceExclVat) {
        return priceExclVat * 1.21; // Veronderstel 21% BTW
    }
}
