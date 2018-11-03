package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateTeamDetailDto {

    @ApiModelProperty(example = "This team is excellent")
    private String description;

}
