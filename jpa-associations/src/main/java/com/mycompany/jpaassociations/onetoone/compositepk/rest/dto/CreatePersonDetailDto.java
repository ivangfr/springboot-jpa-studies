package com.mycompany.jpaassociations.onetoone.compositepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePersonDetailDto {

    @ApiModelProperty(example = "More information about the person")
    @NotBlank
    private String description;

}
