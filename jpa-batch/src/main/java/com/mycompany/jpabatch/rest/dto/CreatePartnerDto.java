package com.mycompany.jpabatch.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreatePartnerDto {

    @ApiModelProperty(example = "partner1")
    @NotNull
    @NotEmpty
    private String name;

}
