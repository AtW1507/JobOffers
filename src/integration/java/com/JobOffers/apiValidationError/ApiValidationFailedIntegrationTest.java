package com.JobOffers.apiValidationError;

import com.JobOffers.BaseIntegrationTest;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_400_bad_request_and_validation_message_when_request_has_empty_offer() throws Exception {
        //given
        //when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content("""
                                
                                {
                                    "companyName": "",
                                    "salary": "",
                                    "position": ""
                                }
                                """.trim())
                .contentType(MediaType.APPLICATION_JSON + ";charset-UTF-8"));
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List<String> messages = JsonPath.read(json, "$.messages");
        assertThat(messages).containsExactlyInAnyOrder(
                "companyName must not be empty",
                "salary must not be empty",
                "position must not be empty",
                "offerUrl must not be null",
                "offerUrl must not be empty");
    }


}
