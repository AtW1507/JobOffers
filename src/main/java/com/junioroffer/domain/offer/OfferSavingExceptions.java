package com.junioroffer.domain.offer;

import lombok.Getter;

import java.util.List;
@Getter
public class OfferSavingExceptions extends RuntimeException {

    private final List<String> offerUrls;

    public OfferSavingExceptions(String offerUrl) {
        super(String.format("offer with offerUrl [%s] already exists", offerUrl));
        this.offerUrls = List.of(offerUrl);
    }

    public OfferSavingExceptions(String message, List<Offer> offers){
        super(String.format("error" + message + offers.toString()));
        this.offerUrls = offers.stream().map(Offer::offerUrl).toList();
    }
}
