package com.ivanfranchin.jpalocking.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameSetupRequest {

    @Schema(example = "5")
    @Positive
    private int numLives;
}
