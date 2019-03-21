package com.mycompany.jpalocking.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class GameSetupDto {

    @ApiModelProperty(example = "5")
    @Positive
    private int numLives;

}
