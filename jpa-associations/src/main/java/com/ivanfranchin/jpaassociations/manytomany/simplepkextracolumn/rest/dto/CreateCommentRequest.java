package com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

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
