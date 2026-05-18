package com.junioroffer.domain.offer;

import com.junioroffer.domain.offer.dto.OfferDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OfferFilterTest {

    private final InMemoryOfferRepositoryTestImpl repositoryTest = new InMemoryOfferRepositoryTestImpl();
    private final OfferFilter offerFilter = new OfferFilter(repositoryTest);

    @Test
    public void should_return_list_of_offer_to_save_to_dataBase(){
        //given
        repositoryTest.save(OfferMapper.mapFromOfferDtoToOffer(new OfferDto(null, "Si", "Java", "2000", "example.com")));
        List<OfferDto> newOffer = new ArrayList<>();
        newOffer.add(new OfferDto(null,"Google","PHP","4000", "google.com"));
        newOffer.add(new OfferDto(null,"Nowy","C++","5000", "nowy.com"));
        newOffer.add(new OfferDto(null, "Sis", "Javaas", "32000", "example2.com"));
        //when
        List<OfferDto> result = offerFilter.filterOffersToSave(newOffer);
        //then
        assertThat(result.stream().map(OfferDto::company)).containsExactlyInAnyOrder("Google","Nowy","Sis");
        assertThat(result.size()).isEqualTo(3);

    }
}
