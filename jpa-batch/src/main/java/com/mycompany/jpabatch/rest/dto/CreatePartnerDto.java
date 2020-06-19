package com.mycompany.jpabatch.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePartnerDto {

    @Schema(example = "partner1")
    @NotBlank
    private String name;

}
