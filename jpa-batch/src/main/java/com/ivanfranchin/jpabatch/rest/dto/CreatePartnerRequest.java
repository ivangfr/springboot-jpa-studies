package com.ivanfranchin.jpabatch.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePartnerRequest {

    @Schema(example = "partner1")
    @NotBlank
    private String name;
}