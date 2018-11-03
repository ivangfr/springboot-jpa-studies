package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateArticleDto {

    @ApiModelProperty(example = "Comparison between Springboot and Play Framework")
    @NotNull
    @NotEmpty
    private String title;

}
