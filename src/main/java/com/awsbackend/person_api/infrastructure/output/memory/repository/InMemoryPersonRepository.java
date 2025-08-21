//package com.awsbackend.person_api.infrastructure.output.memory.repository;
//
//import com.awsbackend.person_api.domain.model.Person;
//import com.awsbackend.person_api.domain.spi.PersonPersistencePort;
//import org.springframework.stereotype.Repository;
//import java.util.HashMap;
//import java.util.Map;
//
//@Repository
//public class InMemoryPersonRepository implements PersonPersistencePort {
//
//    private final Map<String, Person> db = new HashMap<>();
//
//    @Override
//    public Person save(Person person) {
//        db.put(person.getId(), person);
//        return person;
//    }
//
//    @Override
//    public Person findById(String id) {
//        return db.get(id);
//    }
//}

