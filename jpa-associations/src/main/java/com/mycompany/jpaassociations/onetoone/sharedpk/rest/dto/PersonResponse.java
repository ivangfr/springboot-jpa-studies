package com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto;

import lombok.Value;

@Value
public class PersonResponse {

    Long id;
    String name;
    PersonDetailResponse personDetail;
}
