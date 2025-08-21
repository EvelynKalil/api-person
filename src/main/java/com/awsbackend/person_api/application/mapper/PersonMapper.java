package com.awsbackend.person_api.application.mapper;

import com.awsbackend.person_api.application.dto.PersonRequest;
import com.awsbackend.person_api.application.dto.PersonResponse;
import com.awsbackend.person_api.domain.model.Person;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonMapper {

    public Person toDomain(PersonRequest request) {
        return new Person(request.getId(), request.getName(), request.getEmail());
    }

    public PersonResponse toResponse(Person person) {
        return new PersonResponse(person.getId(), person.getName(), person.getEmail());
    }
}

