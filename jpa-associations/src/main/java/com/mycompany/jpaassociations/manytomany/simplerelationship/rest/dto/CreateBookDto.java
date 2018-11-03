package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateBookDto {

    @ApiModelProperty(example = "Introduction to Java 8")
    @NotNull
    @NotEmpty
    private String name;

}
