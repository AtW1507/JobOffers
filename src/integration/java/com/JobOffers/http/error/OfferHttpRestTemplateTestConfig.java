package com.JobOffers.http.error;

import com.junioroffer.domain.offer.OfferFetchable;
import com.junioroffer.infrastructure.offer.http.OfferHttpClientConfig;
import com.junioroffer.infrastructure.offer.http.OfferHttpClientRestTemplateConfigurationProperties;
import com.junioroffer.infrastructure.offer.http.RestTemplateResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

class OfferHttpRestTemplateTestConfig extends OfferHttpClientConfig {

    public OfferFetchable remoteFetcherOfferClient(int port, int connectionTimeout, int readTimeout){
        OfferHttpClientRestTemplateConfigurationProperties properties = OfferHttpClientRestTemplateConfigurationProperties.builder()
                .uri("http://localhost")
                .port(port)
                .connectionTimeout(connectionTimeout)
                .readTimeout(readTimeout)
                .build();
        RestTemplateResponseErrorHandler errorHandler = restTemplateResponseErrorHandler();
        RestTemplate restTemplate = restTemplate(errorHandler, properties);
        return remoteFetcherOfferClient(restTemplate, properties);
    }
}
