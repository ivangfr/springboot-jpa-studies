package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateTeamDto {

    @ApiModelProperty(example = "Black Team")
    private String name;

}
