package com.mycompany.jpaassociations.onetomany.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateRestaurantDto {

    @ApiModelProperty(example = "Happy Burger")
    private String name;

}
