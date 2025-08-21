package com.awsbackend.person_api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonResponse {
    private String id;
    private String name;
    private String email;
}
