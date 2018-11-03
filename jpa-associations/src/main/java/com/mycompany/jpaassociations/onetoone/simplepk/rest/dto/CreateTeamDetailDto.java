package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateTeamDetailDto {

    @ApiModelProperty(example = "This team is awesome")
    @NotNull
    @NotEmpty
    private String description;

}
