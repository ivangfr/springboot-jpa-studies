package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import lombok.Data;

@Data
public class TeamDto {

    private Long id;
    private String name;
    private TeamDetailDto teamDetail;

}
