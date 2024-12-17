package com.ivanfranchin.jpabatch.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreatePartnerRequest(@Schema(example = "partner1") @NotBlank String name) {
}
