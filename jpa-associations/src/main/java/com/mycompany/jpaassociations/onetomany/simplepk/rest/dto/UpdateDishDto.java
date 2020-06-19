package com.mycompany.jpaassociations.onetomany.simplepk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateDishDto {

    @Schema(example = "Pizza Fungi")
    private String name;

}
