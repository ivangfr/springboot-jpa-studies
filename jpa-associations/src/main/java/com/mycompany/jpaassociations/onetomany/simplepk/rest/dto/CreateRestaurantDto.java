package com.mycompany.jpaassociations.onetomany.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateRestaurantDto {

    @ApiModelProperty(example = "Happy Pizza")
    @NotBlank
    private String name;

}
