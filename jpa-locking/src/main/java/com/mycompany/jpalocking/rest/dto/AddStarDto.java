package com.mycompany.jpalocking.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class AddStarDto {

    @ApiModelProperty(example = "10")
    @Positive
    private int numStars;

}
