package com.example.platenwinkel.exceptions;

public class RecordNotFoundException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    public RecordNotFoundException() {

        super();

    }

    public RecordNotFoundException(String message) {

        super(message);

    }

}

