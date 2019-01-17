package com.mycompany.jpabatch.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePartnerDto {

    @ApiModelProperty(example = "partner1")
    @NotBlank
    private String name;

}
