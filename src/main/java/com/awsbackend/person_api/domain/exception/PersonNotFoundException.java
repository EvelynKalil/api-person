package com.awsbackend.person_api.domain.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String id) {
        super("Person with id " + id + " not found");
    }
}