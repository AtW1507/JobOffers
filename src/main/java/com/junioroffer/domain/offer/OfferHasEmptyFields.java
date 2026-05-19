package com.junioroffer.domain.offer;

class OfferHasEmptyFields extends RuntimeException {
    public OfferHasEmptyFields(String message) {
        super(message);
    }
}
