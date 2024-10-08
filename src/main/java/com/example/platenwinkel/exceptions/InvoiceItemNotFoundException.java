package com.example.platenwinkel.exceptions;

public class InvoiceItemNotFoundException extends RuntimeException{
    public InvoiceItemNotFoundException(String message) {
        super(message);
    }
}
