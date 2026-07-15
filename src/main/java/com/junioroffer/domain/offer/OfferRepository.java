package com.junioroffer.domain.offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    List<Offer> findAllOffer();

    Offer save(Offer offer);

    Optional<Offer> findOfferById(String id);

    List<Offer> saveAll(List<Offer> offers);

    boolean existsByOfferUrl(String offerUrl);
}
