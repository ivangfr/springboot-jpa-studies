package com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdatePersonDto {

    @Schema(example = "Ivan Franchin 2")
    private String name;

}
