package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateArticleDto {

    @Schema(example = "Comparison between Springboot and Play Framework")
    @NotBlank
    private String title;

}
