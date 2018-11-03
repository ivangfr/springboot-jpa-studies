package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateStudentDto {

    @ApiModelProperty(example = "Steve Jobs")
    private String name;

}
