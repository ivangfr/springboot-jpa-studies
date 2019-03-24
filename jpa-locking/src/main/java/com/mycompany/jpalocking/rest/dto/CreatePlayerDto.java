package com.mycompany.jpalocking.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePlayerDto {

    @ApiModelProperty(example = "ivan.franchin")
    @NotBlank
    private String username;

}
