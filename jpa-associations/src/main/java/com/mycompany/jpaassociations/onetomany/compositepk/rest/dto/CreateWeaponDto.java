package com.mycompany.jpaassociations.onetomany.compositepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateWeaponDto {

    @ApiModelProperty(example = "Machine Gun")
    @NotBlank
    private String name;

}
