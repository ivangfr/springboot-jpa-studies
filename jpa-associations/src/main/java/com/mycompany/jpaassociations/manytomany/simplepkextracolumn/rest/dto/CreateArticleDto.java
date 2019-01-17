package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateArticleDto {

    @ApiModelProperty(example = "Comparison between Springboot and Play Framework")
    @NotBlank
    private String title;

}
