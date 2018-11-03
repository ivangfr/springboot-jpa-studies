package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateWriterDto {

    @ApiModelProperty(example = "Steve Jobs")
    private String name;

}
