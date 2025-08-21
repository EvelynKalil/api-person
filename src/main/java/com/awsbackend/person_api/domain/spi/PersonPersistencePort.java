package com.awsbackend.person_api.domain.spi;

import com.awsbackend.person_api.domain.model.Person;

public interface PersonPersistencePort {
    Person save(Person person);
    Person findById(String id);
}
