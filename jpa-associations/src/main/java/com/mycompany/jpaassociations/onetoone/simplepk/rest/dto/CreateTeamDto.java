package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateTeamDto {

    @Schema(example = "Team White")
    @NotBlank
    private String name;

}
