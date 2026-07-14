package com.junioroffer.domain.offer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.io.Serializable;

@Builder

public record OfferResponseDto(        String id,
                                       String companyName,
                                       String position,
                                       String salary,
                                       String offerUrl
) implements Serializable
{
}
