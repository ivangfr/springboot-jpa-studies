package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateTeamDetailDto {

    @Schema(example = "This team is excellent")
    private String description;

}
