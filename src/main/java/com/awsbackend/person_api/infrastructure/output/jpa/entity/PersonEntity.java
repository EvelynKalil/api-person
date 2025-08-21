package com.awsbackend.person_api.infrastructure.output.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person")
@Getter
@Setter
public class PersonEntity {

    @Id
    private String id;
    private String name;
    private String email;
}
