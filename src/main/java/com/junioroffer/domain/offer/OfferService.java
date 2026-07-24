package com.junioroffer.domain.offer;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class OfferService {
    private final OfferFetchable offerFetcher;
    private final OfferRepository offerRepository;

    List<Offer> fetchAllOffersAnSaveAllIfNotExists() {
       List<Offer> jobOffers = fetchOffers();
       final List<Offer> offers = filterNotExistingOffers(jobOffers);
       try{
           return offerRepository.saveAll(offers);
       }catch (OfferDuplicateException duplicateKeyException){
           throw new OfferSavingExceptions(duplicateKeyException.getMessage(), jobOffers);
       }
    }

    private  List<Offer> fetchOffers(){
        return offerFetcher.fetchOffers()
                .stream()
                .map(OfferMapper::mapFromJobOfferResponseToOffer)
                .toList();
    }

    private List<Offer> filterNotExistingOffers(List<Offer> jobOffers){
        return jobOffers.stream()
                .filter(offerDto -> !offerDto.offerUrl().isBlank())
                .filter(offerDto -> !offerRepository.existsByOfferUrl(offerDto.offerUrl()))
                .collect(Collectors.toList());
    }
}
