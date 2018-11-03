package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateCourseDto {

    @ApiModelProperty(example = "Java 9")
    private String name;

}
