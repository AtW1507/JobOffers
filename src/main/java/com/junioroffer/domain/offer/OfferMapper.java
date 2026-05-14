package com.junioroffer.domain.offer;

import com.junioroffer.domain.offer.dto.OfferDto;

class OfferMapper {
    public static OfferDto mapFromOfferToOfferDto(Offer offer){
        return OfferDto.builder()
                .id(offer.id())
                .company(offer.company())
                .title(offer.title())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }

    public static Offer mapFromOfferDtoToOffer(OfferDto offerDto){
        return Offer.builder()
                .id(offerDto.id())
                .company(offerDto.company())
                .title(offerDto.title())
                .salary(offerDto.salary())
                .offerUrl(offerDto.offerUrl())
                .build();
    }
}
