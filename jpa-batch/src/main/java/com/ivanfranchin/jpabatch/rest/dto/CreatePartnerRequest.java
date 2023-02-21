package com.ivanfranchin.jpabatch.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePartnerRequest {

    @Schema(example = "partner1")
    @NotBlank
    private String name;
}
