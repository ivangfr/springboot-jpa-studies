package com.mycompany.jpaassociations.onetomany.compositepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePlayerDto {

    @ApiModelProperty(example = "Ivan Franchin")
    @NotBlank
    private String name;

}
