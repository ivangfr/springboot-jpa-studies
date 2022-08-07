package com.ivanfranchin.jpaassociations.onetomany.compositepk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlayerRequest {

    @Schema(example = "Ivan Franchin")
    @NotBlank
    private String name;
}
