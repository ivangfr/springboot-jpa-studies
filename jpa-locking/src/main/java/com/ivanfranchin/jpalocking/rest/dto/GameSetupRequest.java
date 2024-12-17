package com.ivanfranchin.jpalocking.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record GameSetupRequest(@Schema(example = "5") @Positive int numLives) {
}
