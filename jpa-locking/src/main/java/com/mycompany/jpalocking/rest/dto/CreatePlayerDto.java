package com.mycompany.jpalocking.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePlayerDto {

    @Schema(example = "ivan.franchin")
    @NotBlank
    private String username;

}
