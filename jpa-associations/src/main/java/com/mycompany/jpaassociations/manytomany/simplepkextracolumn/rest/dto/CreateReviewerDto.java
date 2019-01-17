package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateReviewerDto {

    @ApiModelProperty(example = "Ivan Franchin")
    @NotBlank
    private String name;

}
