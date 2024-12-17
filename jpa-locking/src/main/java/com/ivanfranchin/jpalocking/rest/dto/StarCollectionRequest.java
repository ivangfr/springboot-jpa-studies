package com.ivanfranchin.jpalocking.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record StarCollectionRequest(@Schema(example = "10") @Positive int numStars) {
}
