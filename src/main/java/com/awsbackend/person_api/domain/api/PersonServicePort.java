package com.awsbackend.person_api.domain.api;

import com.awsbackend.person_api.domain.model.Person;

public interface PersonServicePort {
    Person savePerson(Person person);
    Person getPersonById(String id);
}
