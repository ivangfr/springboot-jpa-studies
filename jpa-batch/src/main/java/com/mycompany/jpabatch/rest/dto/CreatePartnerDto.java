package com.mycompany.jpabatch.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreatePartnerDto {

    @ApiModelProperty(example = "partner1")
    private String name;

}
