package com.mycompany.jpaassociations.onetoone.simplepk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateTeamDto {

    @Schema(example = "Black Team")
    private String name;

}
