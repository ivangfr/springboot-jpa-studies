package com.mycompany.jpaassociations.onetomany.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateDishDto {

    @ApiModelProperty(example = "Pizza Salami")
    @NotNull
    @NotEmpty
    private String name;

}
