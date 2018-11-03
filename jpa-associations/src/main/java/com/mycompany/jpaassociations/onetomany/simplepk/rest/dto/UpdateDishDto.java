package com.mycompany.jpaassociations.onetomany.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateDishDto {

    @ApiModelProperty(example = "Pizza Fungi")
    private String name;

}
