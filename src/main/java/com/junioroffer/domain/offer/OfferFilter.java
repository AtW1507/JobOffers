package com.junioroffer.domain.offer;

import com.junioroffer.domain.offer.dto.OfferDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
class OfferFilter {

    private final OfferRepository offerRepository;

    public List<OfferDto> filterOffersToSave(List<OfferDto> offers) {
        List<Offer> offersByDataBase = offerRepository.findAllOffer();
        Set<String> existingUrl = offersByDataBase.stream().map(Offer::offerUrl)
                .collect(Collectors.toSet());
        return offers.stream().filter(offerDto -> !existingUrl.contains(offerDto.offerUrl()))
                .collect(Collectors.toList());
    }
}
