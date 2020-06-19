package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCourseStudentDto {

    @Schema(example = "9")
    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Short grade;

}
