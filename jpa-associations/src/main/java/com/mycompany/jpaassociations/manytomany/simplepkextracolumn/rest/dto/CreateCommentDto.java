package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateCommentDto {

    @Schema(example = "2")
    @NotNull
    private Long reviewerId;

    @Schema(example = "1")
    @NotNull
    private Long articleId;

    @Schema(example = "This article is very interesting")
    @NotBlank
    private String text;

}
