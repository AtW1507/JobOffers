package com.JobOffers;


import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.junioroffer.JobOffersSpringBootApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import tools.jackson.databind.ObjectMapper;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(classes = JobOffersSpringBootApplication.class)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Testcontainers
public class BaseIntegrationTest {

    static {
        System.setProperty("DOCKER_HOST", "tcp://localhost:2375");
        System.setProperty("DOCKER_TLS_VERIFY", "0");
    }

    public static final String WIRE_MOCK_HOST = "http://localhost";

    @Autowired
    private WebApplicationContext webApplicationContext;

    public MockMvc mockMvc;

    @BeforeEach
    void setUpMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Autowired
    public ObjectMapper objectMapper;


    @Container
    @ServiceConnection
    public static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:5.0.15"));

    static {
        mongoDBContainer.start();
    }


    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();


    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("offer.fetcher-offer.http.client.config.port", () -> wireMockServer.getPort());
        registry.add("offer.fetcher-offer.http.client.config.uri", () -> WIRE_MOCK_HOST);
    }
}