package com.junioroffer.domain.offer;

class OfferConfiguration {
    OfferFacade createForTest(OfferRepository repository){
        OfferFilter offerFilter = new OfferFilter(repository);
        return new OfferFacade(repository,offerFilter);
    }
}
