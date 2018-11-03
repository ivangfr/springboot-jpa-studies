package com.mycompany.jpaassociations.onetoone.compositepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreatePersonDto {

    @ApiModelProperty(example = "Ivan Franchin")
    @NotNull
    @NotEmpty
    private String name;

}
