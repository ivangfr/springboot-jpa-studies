package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateBookDto {

    @ApiModelProperty(example = "Introduction to Java 8")
    @NotBlank
    private String name;

}
