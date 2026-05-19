package com.junioroffer.domain.offer;

import lombok.Builder;




@Builder
public record Offer(
        Long id,
        String company,
        String title,
        String salary,
        String offerUrl
) {

}
