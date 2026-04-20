package com.junioroffer.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferDto(Long id,
                       String company,
                       String title,
                       String salary,
                       String offerUrl) {
}
