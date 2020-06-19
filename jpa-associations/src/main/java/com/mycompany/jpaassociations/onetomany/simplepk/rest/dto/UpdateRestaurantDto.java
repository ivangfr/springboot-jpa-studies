package com.mycompany.jpaassociations.onetomany.simplepk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateRestaurantDto {

    @Schema(example = "Happy Burger")
    private String name;

}
