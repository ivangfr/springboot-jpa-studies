package com.ivanfranchin.jpaassociations.onetomany.compositepk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWeaponRequest {

    @Schema(example = "Machine Gun")
    @NotBlank
    private String name;
}
