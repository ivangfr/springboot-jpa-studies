package com.mycompany.jpaassociations.onetoone.compositepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreatePersonDetailDto {

    @ApiModelProperty(example = "More information about the person")
    @NotNull
    @NotEmpty
    private String description;

}
