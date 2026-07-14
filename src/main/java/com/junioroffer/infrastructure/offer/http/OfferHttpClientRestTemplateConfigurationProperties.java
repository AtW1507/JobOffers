package com.junioroffer.infrastructure.offer.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "offer.fetcher-offer.http.client.config")
@Builder
public record OfferHttpClientRestTemplateConfigurationProperties(long connectionTimeout,int port, long readTimeout, String url) {
}
