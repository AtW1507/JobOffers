package com.junioroffer.infrastructure.offer.controller;

import com.junioroffer.domain.offer.OfferFacade;
import com.junioroffer.domain.offer.dto.OfferRequestDto;
import com.junioroffer.domain.offer.dto.OfferResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/offers/{id}")
    public ResponseEntity<OfferResponseDto> findOfferById(@PathVariable String id){
        OfferResponseDto offerById = offerFacade.findOfferById(id);
        return ResponseEntity.ok(offerById);
    }
    @PostMapping("/offers")
    public ResponseEntity<OfferResponseDto> addOffer(@RequestBody @Valid OfferRequestDto requestDto){
        OfferResponseDto saveOffer = offerFacade.saveOffer(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveOffer);
    }
}
