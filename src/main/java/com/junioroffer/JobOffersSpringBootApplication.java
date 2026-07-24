package com.junioroffer;

import com.junioroffer.infrastructure.offer.http.OfferHttpClientRestTemplateConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({OfferHttpClientRestTemplateConfigurationProperties.class})
@EnableMongoRepositories
@EnableScheduling
public class JobOffersSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobOffersSpringBootApplication.class, args);
    }

}
