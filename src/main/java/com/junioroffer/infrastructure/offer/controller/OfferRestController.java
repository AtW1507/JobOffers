package com.junioroffer.infrastructure.offer.controller;

import com.junioroffer.domain.offer.OfferFacade;
import com.junioroffer.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
public class OfferRestController {

    private final OfferFacade offerFacade;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponseDto>> findAllOffers(){
        List<OfferResponseDto> allOffers = offerFacade.findAllOffers();
        return ResponseEntity.ok(allOffers);
    }
}
