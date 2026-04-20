package com.junioroffer.domain.offer;

import com.junioroffer.domain.offer.dto.OfferDto;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
class OfferFacade {

    private final OfferRepository offerRepository;

    public OfferDto findOfferById(Long id){
        Offer offerById = offerRepository.findOfferById(id);
        return OfferDto.builder()
                .id(offerById.id())
                .company(offerById.company())
                .title(offerById.title())
                .salary(offerById.salary())
                .offerUrl(offerById.offerUrl())
                .build();

    }

    OfferDto saveOffer(OfferDto offerDto) {
        Offer addedOffer = offerRepository.addOffer(new Offer(offerDto.id(),offerDto.company(), offerDto.title(), offerDto.salary(), offerDto.offerUrl()));
        return OfferDto.builder()
                .id(addedOffer.id())
                .company(addedOffer.company())
                .title(addedOffer.title())
                .salary(addedOffer.salary())
                .offerUrl(addedOffer.offerUrl())
                .build();

    }

    public List<OfferDto> findAllOffers(){
        List<Offer> allOffers = offerRepository.findAllOffer();
        return allOffers.stream()
                .map(offer -> OfferDto.builder()
                        .id(offer.id())
                        .company(offer.company())
                        .title(offer.title())
                        .salary(offer.salary())
                        .offerUrl(offer.offerUrl())
                        .build())
                .toList();

    }
}
