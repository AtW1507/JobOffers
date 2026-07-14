package com.junioroffer.infrastructure.offer.http;

import com.junioroffer.domain.offer.OfferFetchable;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
@Configuration
public class OfferHttpClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler(){
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMillis(1000));
        requestFactory.setReadTimeout(Duration.ofMillis(1000));

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
        return restTemplate;
    }

    @Bean
    public OfferFetchable remoteFetcherOfferClient(RestTemplate restTemplate,
                                                   @Value("${offer.fetcher-offer.http.client.config.uri}") String uri,
                                                   @Value("${offer.fetcher-offer.http.client.config.port}") int port){
        return new OfferHttpClient(restTemplate, uri, port);
    }
}
