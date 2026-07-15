package com.junioroffer;

import com.junioroffer.infrastructure.offer.http.OfferHttpClientRestTemplateConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({OfferHttpClientRestTemplateConfigurationProperties.class})
public class JobOffersSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobOffersSpringBootApplication.class, args);
    }

}
