package com.mycompany.jpalocking.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class GameSetupDto {

    @Schema(example = "5")
    @Positive
    private int numLives;

}
