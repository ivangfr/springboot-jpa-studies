package com.mycompany.jpalocking.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class CreatePlayerDto {

    @ApiModelProperty(example = "ivan.franchin")
    @NotBlank
    private String username;

    @ApiModelProperty(example = "20")
    @Positive
    private int numStars;

}
