package com.junioroffer.domain.offer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;


import java.util.List;


@Builder
public record JobOfferResponse(
        String title,
        String company,
        String salary,
        String offerUrl
) {
}
