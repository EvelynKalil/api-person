package com.awsbackend.person_api.infrastructure.input.rest;

import com.awsbackend.person_api.domain.api.PersonServicePort;
import com.awsbackend.person_api.domain.exception.PersonNotFoundException;
import com.awsbackend.person_api.domain.model.Person;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonServicePort personService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        PersonServicePort personServicePort() {
            return Mockito.mock(PersonServicePort.class);
        }
    }

    @Test
    void savePerson_shouldReturnSavedPerson() throws Exception {
        Person person = new Person("1", "Evelyn", "evelyn@mail.com");
        when(personService.savePerson(any(Person.class))).thenReturn(person);

        mockMvc.perform(post("/person/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Evelyn\",\"email\":\"evelyn@mail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Evelyn"))
                .andExpect(jsonPath("$.email").value("evelyn@mail.com"));
    }

    @Test
    void getPersonById_shouldReturnPerson() throws Exception {
        Person person = new Person("1", "Evelyn", "evelyn@mail.com");
        when(personService.getPersonById("1")).thenReturn(person);

        mockMvc.perform(get("/person/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Evelyn"))
                .andExpect(jsonPath("$.email").value("evelyn@mail.com"));
    }

    @Test
    void getPersonById_shouldReturn404IfNotFound() throws Exception {
        when(personService.getPersonById("99"))
                .thenThrow(new PersonNotFoundException("99"));

        mockMvc.perform(get("/person/get/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Person with id 99 not found"));
    }

    @Test
    void savePerson_shouldReturn400IfInvalid() throws Exception {
        // name vacío -> debe fallar la validación de @Valid en el controller
        mockMvc.perform(post("/person/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"\",\"email\":\"not-an-email\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void getPersonById_shouldReturn500OnUnexpectedError() throws Exception {
        when(personService.getPersonById("500"))
                .thenThrow(new RuntimeException("Unexpected failure"));

        mockMvc.perform(get("/person/get/500"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Unexpected error occurred"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.timestamp").exists());
    }

}
