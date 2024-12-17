package com.ivanfranchin.jpalocking.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreatePlayerRequest(@Schema(example = "ivan.franchin") @NotBlank String username) {
}
