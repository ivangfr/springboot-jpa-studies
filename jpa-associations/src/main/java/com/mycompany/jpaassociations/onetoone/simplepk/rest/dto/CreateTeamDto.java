package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateTeamDto {

    @ApiModelProperty(example = "Team White")
    @NotBlank
    private String name;

}
