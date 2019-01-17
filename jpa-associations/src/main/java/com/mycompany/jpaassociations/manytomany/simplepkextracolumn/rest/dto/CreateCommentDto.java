package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateCommentDto {

    @ApiModelProperty(example = "2")
    @NotNull
    private Long reviewerId;

    @ApiModelProperty(position = 2, example = "1")
    @NotNull
    private Long articleId;

    @ApiModelProperty(position = 3, example = "This article is very interesting")
    @NotBlank
    private String text;

}
