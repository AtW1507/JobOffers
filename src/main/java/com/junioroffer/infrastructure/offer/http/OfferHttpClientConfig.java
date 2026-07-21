package com.junioroffer.infrastructure.offer.http;

import com.junioroffer.domain.offer.OfferFetchable;

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
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler, OfferHttpClientRestTemplateConfigurationProperties properties){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()));
        requestFactory.setReadTimeout(Duration.ofMillis(properties.readTimeout()));

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
        return restTemplate;
    }

    @Bean
    public OfferFetchable remoteFetcherOfferClient(RestTemplate restTemplate,
                                                  OfferHttpClientRestTemplateConfigurationProperties properties){
        return new OfferHttpClient(restTemplate, properties.uri(), properties.port());
    }
}
