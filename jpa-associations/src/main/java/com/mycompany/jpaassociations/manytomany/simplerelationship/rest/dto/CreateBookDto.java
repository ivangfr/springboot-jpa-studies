package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateBookDto {

    @Schema(example = "Introduction to Java 8")
    @NotBlank
    private String name;

}
