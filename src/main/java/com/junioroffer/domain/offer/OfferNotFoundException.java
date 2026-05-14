package com.junioroffer.domain.offer;

class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException (String message){
        super(message);
    }
}
