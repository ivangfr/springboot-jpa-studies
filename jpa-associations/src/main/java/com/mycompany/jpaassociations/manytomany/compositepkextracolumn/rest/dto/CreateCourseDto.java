package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCourseDto {

    @ApiModelProperty(example = "Java 8")
    @NotBlank
    private String name;

}
