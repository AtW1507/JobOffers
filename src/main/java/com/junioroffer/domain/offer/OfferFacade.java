package com.junioroffer.domain.offer;

import com.junioroffer.domain.offer.dto.OfferDto;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferFilter offerFilter;

    public OfferDto findOfferById(Long id) {
        return offerRepository.findOfferById(id)
                .map(OfferMapper::mapFromOfferToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException("Offer with this id: " + id + " not found"));
    }


    public OfferDto saveOffer(OfferDto offerDto) {
        if (offerDto.hasEmptyFields()) {
            throw new OfferHasEmptyFields("Offer has empty fields");
        }
        Offer addedOffer = offerRepository.save(OfferMapper.mapFromOfferDtoToOffer(offerDto));
        return OfferMapper.mapFromOfferToOfferDto(addedOffer);


    }

    public List<OfferDto> findAllOffers() {
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

    public List<OfferDto> fetchAllOffersAndSaveAllIfNotExists(List<OfferDto> offers) {
        List<OfferDto> offersToSave = offerFilter.filterOffersToSave(offers);
        List<Offer> offersEntity = offersToSave.stream().map(OfferMapper::mapFromOfferDtoToOffer).toList();
        List<Offer> savedAllOffers = offerRepository.saveAll(offersEntity);
        return savedAllOffers.stream().map(OfferMapper::mapFromOfferToOfferDto).toList();
    }


}
