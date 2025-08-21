package com.awsbackend.person_api.domain.usecase;

import com.awsbackend.person_api.domain.api.PersonServicePort;
import com.awsbackend.person_api.domain.model.Person;
import com.awsbackend.person_api.domain.spi.PersonPersistencePort;
import com.awsbackend.person_api.domain.exception.PersonNotFoundException;

public class PersonUseCase implements PersonServicePort {

    private final PersonPersistencePort repository;

    public PersonUseCase(PersonPersistencePort repository) {
        this.repository = repository;
    }

    @Override
    public Person savePerson(Person person) {
        return repository.save(person);
    }

    @Override
    public Person getPersonById(String id) {
        var person = repository.findById(id);
        if (person == null) {
            throw new PersonNotFoundException(id);
        }
        return person;
    }

}
