package com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto;

import lombok.Value;

@Value
public class PersonDetailResponse {

    Long id;
    String description;
}
