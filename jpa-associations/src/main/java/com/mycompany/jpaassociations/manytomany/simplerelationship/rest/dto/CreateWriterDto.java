package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateWriterDto {

    @ApiModelProperty(example = "Ivan Franchin")
    @NotBlank
    private String name;

}
