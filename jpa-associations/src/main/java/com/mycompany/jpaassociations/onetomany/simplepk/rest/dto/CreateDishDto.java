package com.mycompany.jpaassociations.onetomany.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateDishDto {

    @ApiModelProperty(example = "Pizza Salami")
    @NotBlank
    private String name;

}
