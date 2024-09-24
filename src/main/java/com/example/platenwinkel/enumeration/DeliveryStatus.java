package com.example.platenwinkel.enumeration;

public enum DeliveryStatus {

    PENDING,        // De bestelling is geplaatst, maar nog niet verwerkt voor verzending.
    PROCESSING,     // De bestelling wordt momenteel verwerkt en voorbereid voor verzending.
    SHIPPED,        // De bestelling is verzonden en onderweg naar de klant.
    IN_TRANSIT,     // De bestelling is onderweg, maar nog niet bij de klant aangekomen.
    DELIVERED,      // De bestelling is succesvol afgeleverd aan de klant.
    CANCELED,       // De bestelling is geannuleerd, mogelijk vanwege een probleem of verzoek van de klant.
    RETURNED,       // De bestelling is door de klant geretourneerd.
    FAILED

}
