package com.junioroffer.domain.offer;

import com.junioroffer.domain.offer.dto.OfferDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;



import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


class OfferFacadeTest {

    InMemoryOfferRepositoryTestImpl repositoryTest = new InMemoryOfferRepositoryTestImpl();

    OfferFacade offerFacade = new OfferConfiguration().createForTest(repositoryTest);

    @Test
    public void should_return_offer_when_added_offer() {
        //given
        OfferDto offer = new OfferDto(null, "Si", "Junior JAVA", "2000", "https://example.com");
        //when
        OfferDto savedOffer = offerFacade.saveOffer(offer);
        //then
        assertThat(savedOffer).isEqualTo(
                OfferDto.builder()
                        .id(0L)
                        .company("Si")
                        .title("Junior JAVA")
                        .salary("2000")
                        .offerUrl("https://example.com")
                        .build()
        );
    }

    @Test
    public void should_return_offer_by_id() {
        // given
        OfferDto offerToSave = new OfferDto(null, "Si", "Java", "2000", "example.com");
        OfferDto savedOffer = offerFacade.saveOffer(offerToSave);
        Long offerId = savedOffer.id();
        //when
        OfferDto result = offerFacade.findOfferById(offerId);
        //then

        assertThat(result.company()).isEqualTo("Si");
        assertThat(result.id()).isEqualTo(offerId);

    }
    @Test
    public void should_return_error_message_when_offer_not_found(){
        //given
        //when
        Throwable throwable = catchThrowable(() -> offerFacade.findOfferById(0L));
        //then
        assertThat(throwable).isInstanceOf(OfferNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Offer with this id: " + 0 + " not found");
    }

    @Test
    public void should_return_all_offer() {
        //given
        offerFacade.saveOffer(new OfferDto(null, "Si", "Java", "2000", "example.com"));
        offerFacade.saveOffer(new OfferDto(null, "Sis", "Javaas", "32000", "example2.com"));
        //when
        List<OfferDto> allOffers = offerFacade.findAllOffers();
        //then
        assertThat(allOffers.size()).isEqualTo(2);
        assertThat(allOffers.stream()
                .map(OfferDto::company)).containsExactlyInAnyOrder("Si", "Sis");


    }
    @Test
    public void should_return_all_saved_offer_when_this_offer_not_have_in_dataBase(){
        //given
        offerFacade.saveOffer(new OfferDto(null, "Si", "Java", "2000", "example.com"));
        offerFacade.saveOffer(new OfferDto(null, "Sis", "Javaas", "32000", "example2.com"));
        List<OfferDto> newOffer = new ArrayList<>();
        newOffer.add(new OfferDto(null,"Google","PHP","4000", "google.com"));
        newOffer.add(new OfferDto(null,"Nowy","C++","5000", "nowy.com"));
        newOffer.add(new OfferDto(null, "Sis", "Javaas", "32000", "example2.com"));
        //when
        List<OfferDto> offerSaved = offerFacade.fetchAllOffersAndSaveAllIfNotExists(newOffer);
        List<OfferDto> allOffers = offerFacade.findAllOffers();
        //then
        assertThat(offerSaved.size()).isEqualTo(2);
        assertThat(allOffers.size()).isEqualTo(4);
        assertThat(allOffers.stream().map(OfferDto::company)).containsExactlyInAnyOrder("Si","Sis","Nowy","Google");
    }
    @Test
    public void should_return_error_message_when_offer_has_empty_fields(){
        //given
        //when
        Throwable throwable = catchThrowable(()->offerFacade.saveOffer(new OfferDto(null, " ", "Java", "", "example.com")));
        //then
        assertThat(throwable).isInstanceOf(OfferHasEmptyFields.class);
        assertThat(throwable.getMessage()).isEqualTo("Offer has empty fields");


    }

}