package com.mycompany.jpaassociations.onetomany.compositepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreatePlayerDto {

    @ApiModelProperty(example = "Ivan Franchin")
    @NotNull
    @NotEmpty
    private String name;

}
