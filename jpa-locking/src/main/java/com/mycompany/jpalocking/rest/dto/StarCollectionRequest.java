package com.mycompany.jpalocking.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarCollectionRequest {

    @Schema(example = "10")
    @Positive
    private int numStars;
}
