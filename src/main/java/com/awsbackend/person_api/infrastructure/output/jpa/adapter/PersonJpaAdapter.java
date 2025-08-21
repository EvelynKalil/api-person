package com.awsbackend.person_api.infrastructure.output.jpa.adapter;

import com.awsbackend.person_api.domain.model.Person;
import com.awsbackend.person_api.domain.spi.PersonPersistencePort;
import com.awsbackend.person_api.infrastructure.output.jpa.entity.PersonEntity;
import com.awsbackend.person_api.infrastructure.output.jpa.repository.PersonJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class PersonJpaAdapter implements PersonPersistencePort {

    private final PersonJpaRepository repository;

    public PersonJpaAdapter(PersonJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person save(Person person) {
        PersonEntity entity = new PersonEntity();
        entity.setId(person.getId());
        entity.setName(person.getName());
        entity.setEmail(person.getEmail());
        PersonEntity saved = repository.save(entity);
        return new Person(saved.getId(), saved.getName(), saved.getEmail());
    }

    @Override
    public Person findById(String id) {
        return repository.findById(id)
                .map(e -> new Person(e.getId(), e.getName(), e.getEmail()))
                .orElse(null);
    }
}
