package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateStudentDto {

    @Schema(example = "Steve Jobs")
    private String name;

}
