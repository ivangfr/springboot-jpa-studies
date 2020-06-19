package com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdatePersonDetailDto {

    @Schema(example = "New information about the person")
    private String description;

}
