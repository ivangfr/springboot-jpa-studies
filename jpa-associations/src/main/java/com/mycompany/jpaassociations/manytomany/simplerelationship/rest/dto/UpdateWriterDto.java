package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateWriterDto {

    @Schema(example = "Steve Jobs")
    private String name;

}
