package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateTeamDetailDto {

    @ApiModelProperty(example = "This team is awesome")
    @NotBlank
    private String description;

}
