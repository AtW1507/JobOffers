package com.junioroffer.domain.offer;

import com.junioroffer.domain.offer.dto.OfferRequestDto;
import com.junioroffer.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferService offerService;

    public OfferResponseDto findOfferById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapFromOfferToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException("Offer with id: " + id + " not found"));
    }


    public OfferResponseDto saveOffer(OfferRequestDto offerDto) {
        final Offer offer = OfferMapper.mapFromOfferDtoToOffer(offerDto);
        final Offer save = offerRepository.save(offer);
        return OfferMapper.mapFromOfferToOfferDto(save);


    }
    @Cacheable("jobOffers")
    public List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAll().stream()
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
