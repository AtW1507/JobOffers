package com.junioroffer.domain.offer;

import com.junioroffer.domain.offer.dto.OfferRequestDto;
import com.junioroffer.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferService offerService;

    public OfferResponseDto findOfferById(String id) {
        return offerRepository.findOfferById(id)
                .map(OfferMapper::mapFromOfferToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException("Offer with this id: " + id + " not found"));
    }


    public OfferResponseDto saveOffer(OfferRequestDto offerDto) {
        final Offer offer = OfferMapper.mapFromOfferDtoToOffer(offerDto);
        final Offer save = offerRepository.save(offer);
        return OfferMapper.mapFromOfferToOfferDto(save);


    }

    public List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAllOffer().stream()
                .map(OfferMapper::mapFromOfferToOfferDto)
                .collect(Collectors.toList());

    }

    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        return offerService.fetchAllOffersAnSaveAllIfNotExists()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferDto)
                .toList();
    }


}
