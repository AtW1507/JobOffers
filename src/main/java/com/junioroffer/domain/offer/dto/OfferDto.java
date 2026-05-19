package com.junioroffer.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferDto(Long id,
                       String company,
                       String title,
                       String salary,
                       String offerUrl) {
    public boolean hasEmptyFields() {
        return company == null || company.isBlank()
                || title == null || title.isBlank()
                || salary == null || salary.isBlank()
                || offerUrl == null || offerUrl.isBlank();
    }
}
