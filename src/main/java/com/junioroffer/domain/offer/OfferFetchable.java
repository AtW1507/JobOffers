package com.junioroffer.domain.offer;

import com.junioroffer.domain.offer.dto.JobOfferResponse;


import java.util.List;

public interface OfferFetchable {

    List<JobOfferResponse> fetchOffers();
}
