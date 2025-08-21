package com.awsbackend.person_api.integration;

import com.awsbackend.person_api.application.dto.PersonRequest;
import com.awsbackend.person_api.application.dto.PersonResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void saveAndGetPerson_shouldWorkEndToEnd() {
        String baseUrl = "http://localhost:" + port + "/person";

        // 1. Guardar persona
        PersonRequest request = new PersonRequest();
        request.setId("200");
        request.setName("IntegrationTest");
        request.setEmail("integration@test.com");

        ResponseEntity<PersonResponse> postResponse = restTemplate.postForEntity(
                baseUrl + "/save",
                request,
                PersonResponse.class
        );

        assertThat(postResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getId()).isEqualTo("200");

        // 2. Consultar persona
        ResponseEntity<PersonResponse> getResponse = restTemplate.exchange(
                baseUrl + "/get/200",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                PersonResponse.class
        );

        assertThat(getResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getEmail()).isEqualTo("integration@test.com");
    }
}
