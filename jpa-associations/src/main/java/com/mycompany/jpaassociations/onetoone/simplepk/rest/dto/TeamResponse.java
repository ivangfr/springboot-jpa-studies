package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import lombok.Value;

@Value
public class TeamResponse {

    Long id;
    String name;
    TeamDetailResponse teamDetail;
}
