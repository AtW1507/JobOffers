package com.junioroffer.domain.offer;

import java.util.List;

public interface OfferRepository {

    List<Offer> findAllOffer();

    Offer addOffer(Offer offer);

    Offer findOfferById(Long id);
}
