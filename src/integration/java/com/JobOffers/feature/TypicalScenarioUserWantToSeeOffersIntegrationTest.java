package com.JobOffers.feature;

import com.JobOffers.SampleJobOfferResponse;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.junioroffer.domain.loginandregister.dto.RegistrationResultDto;
import com.junioroffer.domain.offer.dto.OfferResponseDto;
import com.junioroffer.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import com.junioroffer.infrastructure.offer.scheduler.OfferScheduler;
import org.junit.jupiter.api.Test;
import com.JobOffers.BaseIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import tools.jackson.core.type.TypeReference;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class TypicalScenarioUserWantToSeeOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOfferResponse {

    @Autowired
    OfferScheduler offerScheduler;

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5.0.15"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {
//    step 1: there are no offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithZeroOffersJson())));


//    step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //given && when
        List<OfferResponseDto> newOffers = offerScheduler.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(newOffers).isEmpty();


//    step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        // given && then
        ResultActions failedLoginRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                         "userName" : "someUser",
                         "password" : "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        failedLoginRequest
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(
                        """
                                    {
                                        "message": "Bad Credentials",
                                        "status": "UNAUTHORIZED"
                                    }
                                """.trim()));


//    step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        //given && when
        ResultActions failedGetOffersRequest = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        failedGetOffersRequest.andExpect(status().isForbidden());


//    step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        //given
        ResultActions registerAction = mockMvc.perform(post("/register")
                .content("""
                        {
                         "userName" : "someUser",
                         "password" : "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult registerActionResult = registerAction.andExpect(status().isCreated()).andReturn();
        String registerActionResultJson = registerActionResult.getResponse().getContentAsString();
        RegistrationResultDto registrationResultDto = objectMapper.readValue(registerActionResultJson, RegistrationResultDto.class);
        assertAll(
                () -> assertThat(registrationResultDto.userName()).isEqualTo("someUser"),
                () -> assertThat(registrationResultDto.created()).isTrue(),
                () -> assertThat(registrationResultDto.id()).isNotNull()
        );


//    step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        //given & when
        ResultActions successLoginRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                         "userName" : "someUser",
                         "password" : "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult1 = successLoginRequest.andExpect(status().isOk()).andReturn();
        String json = mvcResult1.getResponse().getContentAsString();
        JwtResponseDto jwtResponseDto = objectMapper.readValue(json, JwtResponseDto.class);
        String token = jwtResponseDto.token();
        assertAll(
                () -> assertThat(jwtResponseDto.userName()).isEqualTo("someUser"),
                () -> assertThat(jwtResponseDto.token()).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );


//    step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        // given && then
        ResultActions perform = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = perform.andExpect(status().isOk()).andReturn();
        String jsonGet = mvcResult.getResponse().getContentAsString();
        List<OfferResponseDto> offers = objectMapper.readValue(jsonGet, new TypeReference<>() {
        });

        assertThat(offers).isEmpty();


//    step 8: there are 2 new offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithTwoOffersJson())));


//    step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        // given && then
        List<OfferResponseDto> twoNewOffers = offerScheduler.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(twoNewOffers).hasSize(2);


//    step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000
        // given && then
        ResultActions performResultWithTwoOffers = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token));
        //then
        MvcResult mvcResultWithTwoOffers = performResultWithTwoOffers.andExpect(status().isOk()).andReturn();
        String jsonWithTwoOffers = mvcResultWithTwoOffers.getResponse().getContentAsString();
        List<OfferResponseDto> offerResponseDtoWithTwoOffers = objectMapper.readValue(jsonWithTwoOffers, new TypeReference<>() {
        });
        assertThat(offerResponseDtoWithTwoOffers).hasSize(2);
        OfferResponseDto firstOffer = offerResponseDtoWithTwoOffers.get(0);
        OfferResponseDto secundOffer = offerResponseDtoWithTwoOffers.get(1);
        assertThat(offerResponseDtoWithTwoOffers).containsExactlyInAnyOrder(
                new OfferResponseDto(firstOffer.id(), firstOffer.companyName(), firstOffer.position(), firstOffer.salary(), firstOffer.offerUrl()),
                new OfferResponseDto(secundOffer.id(), secundOffer.companyName(), secundOffer.position(), secundOffer.salary(), secundOffer.offerUrl())
        );


//    step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        // given && then
        ResultActions performResultWithNotExistingId = mockMvc.perform(get("/offers/9999")
                .header("Authorization", "Bearer " + token));
        //then
        performResultWithNotExistingId.andExpect(status().isNotFound()).andExpect(content().json(
                """
                        {
                        "message" : "Offer with id: 9999 not found",
                        "status" : "NOT_FOUND"
                        }
                        """.trim()
        ));


//    step 12: user made GET /offers/1000 and system returned OK(200) with offer
        //given
        String offerIdWithDatabase = firstOffer.id();
        //when
        ResultActions performGetOfferById = mockMvc.perform(get("/offers/" + offerIdWithDatabase)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResultForOfferById = performGetOfferById.andExpect(status().isOk()).andReturn();
        String jsonForOfferById = mvcResultForOfferById.getResponse().getContentAsString();
        OfferResponseDto offerResponseDtoForOfferById = objectMapper.readValue(jsonForOfferById, OfferResponseDto.class);

        assertThat(offerResponseDtoForOfferById.id()).isEqualTo(offerIdWithDatabase);
        assertThat(offerResponseDtoForOfferById.companyName()).isEqualTo(firstOffer.companyName());


//    step 13: there are 2 new offers in external HTTP server
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithFourOffersJson())));


//    step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        // given && then
        List<OfferResponseDto> offerResponseDtosWithFourOffers = offerScheduler.fetchAllOffersAndSaveAllIfNotExists();
        //then
        assertThat(offerResponseDtosWithFourOffers).hasSize(2);


//    step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
        // given && then
        ResultActions performWithFourOffers = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token));
        MvcResult mvcResultWithFourOffers = performWithFourOffers.andExpect(status().isOk()).andReturn();
        String jsonForFourOffers = mvcResultWithFourOffers.getResponse().getContentAsString();
        List<OfferResponseDto> fourOffers = objectMapper.readValue(jsonForFourOffers, new TypeReference<>() {
        });
        OfferResponseDto thirdOfferWithDataBase = fourOffers.get(2);
        OfferResponseDto fourOfferWithDataBase = fourOffers.get(3);
        //then
        assertThat(fourOffers).containsExactlyInAnyOrder(
                new OfferResponseDto(firstOffer.id(), firstOffer.companyName(), firstOffer.position(), firstOffer.salary(), firstOffer.offerUrl()),
                new OfferResponseDto(secundOffer.id(), secundOffer.companyName(), secundOffer.position(), secundOffer.salary(), secundOffer.offerUrl()),
                new OfferResponseDto(thirdOfferWithDataBase.id(), thirdOfferWithDataBase.companyName(), thirdOfferWithDataBase.position(), thirdOfferWithDataBase.salary(), thirdOfferWithDataBase.offerUrl()),
                new OfferResponseDto(fourOfferWithDataBase.id(), fourOfferWithDataBase.companyName(), fourOfferWithDataBase.position(), fourOfferWithDataBase.salary(), fourOfferWithDataBase.offerUrl())

        );
        assertThat(fourOffers).hasSize(4);


//    step 16: user made POST /offers with header "Authorization: Bearer AAAA.BBBB.CCCC" and offer as body and system returned CREATED(201) with saved offer
        // given && then
        ResultActions performResultWithPostOffer = mockMvc.perform(post("/offers")
                .header("Authorization", "Bearer " + token)
                .content(
                        """
                                
                                {
                                    "title": "Junior Java Developer",
                                    "companyName": "Connectis_",
                                    "salary": "14 000 – 17 000 PLN",
                                    "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-connectis--warszawa2",
                                    "source": "nofluffjobs",
                                    "salary_estimated": false,
                                    "position" : "Junior"
                                }
                                """.trim()
                ).contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResultForPostOffers = performResultWithPostOffer.andExpect(status().isCreated()).andReturn();
        String jsonForSavedOffer = mvcResultForPostOffers.getResponse().getContentAsString();
        OfferResponseDto offerResponseDto = objectMapper.readValue(jsonForSavedOffer, OfferResponseDto.class);
        String id = offerResponseDto.id();

        assertAll(
                () -> assertThat(offerResponseDto).isNotNull(),
                () -> assertThat(offerResponseDto.offerUrl()).isEqualTo("https://nofluffjobs.com/pl/job/junior-java-developer-connectis--warszawa2"),
                () -> assertThat(offerResponseDto.companyName()).isEqualTo("Connectis_"),
                () -> assertThat(offerResponseDto.salary()).isEqualTo("14 000 – 17 000 PLN"),
                () -> assertThat(offerResponseDto.position()).isEqualTo("Junior"),
                () -> assertThat(id).isNotNull()
        );


//    step 17: user made GET /offers with header "Authorization: Bearer AAAA.BBBB.CCCC" and system returned OK(200) with 5 offer
        // given && then
        ResultActions performForGetOffer = mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResultForGetOneOffer = performForGetOffer.andExpect(status().isOk()).andReturn();
        String jsonForGetOneOffer = mvcResultForGetOneOffer.getResponse().getContentAsString();
        List<OfferResponseDto> offers1 = objectMapper.readValue(jsonForGetOneOffer, new TypeReference<>() {
        });

        assertThat(offers1).isNotEmpty();
        assertThat(offers1).hasSize(5);
        assertThat(offers1.stream().map(OfferResponseDto::id)).contains(id);

    }


}
