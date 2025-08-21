package com.awsbackend.person_api.domain.usecase;

import com.awsbackend.person_api.domain.exception.PersonNotFoundException;
import com.awsbackend.person_api.domain.model.Person;
import com.awsbackend.person_api.domain.spi.PersonPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonUseCaseTest {

    private PersonPersistencePort repository;
    private PersonUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PersonPersistencePort.class);
        useCase = new PersonUseCase(repository);
    }

    @Test
    void savePerson_shouldReturnSavedPerson() {
        // Arrange
        Person person = new Person("1", "Evelyn", "evelyn@mail.com");
        when(repository.save(person)).thenReturn(person);

        // Act
        Person result = useCase.savePerson(person);

        // Assert
        assertEquals("Evelyn", result.getName());
        assertEquals("evelyn@mail.com", result.getEmail());
        verify(repository, times(1)).save(person);
    }

    @Test
    void getPersonById_shouldReturnPersonIfExists() {
        // Arrange
        Person person = new Person("1", "Evelyn", "evelyn@mail.com");
        when(repository.findById("1")).thenReturn(person);

        // Act
        Person result = useCase.getPersonById("1");

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void getPersonById_shouldThrowExceptionIfNotFound() {
        // Arrange
        when(repository.findById("99")).thenReturn(null);

        // Act & Assert
        PersonNotFoundException ex = assertThrows(
                PersonNotFoundException.class,
                () -> useCase.getPersonById("99")
        );
        assertEquals("Person with id 99 not found", ex.getMessage());
    }
}
