package com.junioroffer.domain.offer;

public class OfferHasEmptyFields extends RuntimeException {
    public OfferHasEmptyFields(String message) {
        super(message);
    }
}
