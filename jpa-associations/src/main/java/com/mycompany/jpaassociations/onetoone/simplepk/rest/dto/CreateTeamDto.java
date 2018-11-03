package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateTeamDto {

    @ApiModelProperty(example = "Team White")
    @NotNull
    @NotEmpty
    private String name;

}
