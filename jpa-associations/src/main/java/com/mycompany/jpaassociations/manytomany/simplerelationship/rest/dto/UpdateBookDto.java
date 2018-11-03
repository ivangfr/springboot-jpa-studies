package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateBookDto {

    @ApiModelProperty(example = "Introduction to Springboot")
    private String name;

}
