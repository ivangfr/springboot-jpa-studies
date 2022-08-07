package com.ivanfranchin.jpaassociations.onetoone.sharedpk.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonRequest {

    @Schema(example = "Ivan Franchin")
    @NotBlank
    private String name;
}
