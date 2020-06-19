package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateTeamDetailDto {

    @Schema(example = "This team is awesome")
    @NotBlank
    private String description;

}
