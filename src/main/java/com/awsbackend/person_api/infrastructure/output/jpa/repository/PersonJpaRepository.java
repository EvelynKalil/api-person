package com.awsbackend.person_api.infrastructure.output.jpa.repository;

import com.awsbackend.person_api.infrastructure.output.jpa.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonJpaRepository extends JpaRepository<PersonEntity, String> {
}

