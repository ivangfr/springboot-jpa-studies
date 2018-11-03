package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateCourseDto {

    @ApiModelProperty(example = "Java 8")
    @NotNull
    @NotEmpty
    private String name;

}
