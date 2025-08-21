package com.awsbackend.person_api.infrastructure.configuration;

import com.awsbackend.person_api.domain.api.PersonServicePort;
import com.awsbackend.person_api.domain.spi.PersonPersistencePort;
import com.awsbackend.person_api.domain.usecase.PersonUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PersonServicePort personService(PersonPersistencePort repository) {
        return new PersonUseCase(repository);
    }
}

