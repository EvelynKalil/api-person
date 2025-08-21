package com.awsbackend.person_api.infrastructure.input.rest;

import com.awsbackend.person_api.application.dto.PersonRequest;
import com.awsbackend.person_api.application.dto.PersonResponse;
import com.awsbackend.person_api.application.mapper.PersonMapper;
import com.awsbackend.person_api.domain.api.PersonServicePort;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonServicePort personService;

    public PersonController(PersonServicePort personService) {
        this.personService = personService;
    }

    @PostMapping("/save")
    public PersonResponse savePerson(@Valid @RequestBody PersonRequest request) {
        var person = PersonMapper.toDomain(request);
        return PersonMapper.toResponse(personService.savePerson(person));
    }

    @GetMapping("/get/{id}")
    public PersonResponse getPersonById(@PathVariable String id) {
        var person = personService.getPersonById(id);
        return PersonMapper.toResponse(person);
    }
}

