package com.example.platenwinkel.helper;

public class PriceCalculator {
    public static Double calculatePriceInclVat(Double priceExclVat) {
        validatePriceExclVat(priceExclVat);
        return priceExclVat * 1.21;
    }

    private static void validatePriceExclVat(Double priceExclVat) {
        if (priceExclVat == null || priceExclVat <= 0) {
            throw new IllegalArgumentException("Price excluding VAT must be greater than 0.");
        }
    }
}
