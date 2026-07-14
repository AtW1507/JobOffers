package com.junioroffer.domain.offer;

class OfferConfiguration {
    OfferFacade createForTest(OfferFetchable offerFetchable,OfferRepository repository){
        OfferService offerService = new OfferService(offerFetchable, repository);
        return new OfferFacade(repository,offerService);
    }
}
