package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCourseDto {

    @Schema(example = "Java 8")
    @NotBlank
    private String name;

}
