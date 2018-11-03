package com.mycompany.jpaassociations.onetomany.compositepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateWeaponDto {

    @ApiModelProperty(example = "Machine Gun")
    @NotNull
    @NotEmpty
    private String name;

}
