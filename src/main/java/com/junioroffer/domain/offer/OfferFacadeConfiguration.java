package com.junioroffer.domain.offer;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class OfferFacadeConfiguration {



    @Bean
    OfferFacade offerFacade(OfferRepository offerRepository, OfferFetchable offerFetcher) {
        OfferService offerService = new OfferService(offerFetcher, offerRepository);
        return new OfferFacade(offerRepository, offerService);
    }

}
