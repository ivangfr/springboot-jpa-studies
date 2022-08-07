package com.ivanfranchin.producer.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateOpeningHourRequest {

    @NotBlank
    @Schema(example = "2019-12-31")
    private String date;

    @NotBlank
    @Schema(example = "00:00:00")
    private String begin;

    @NotBlank
    @Schema(example = "23:59:59")
    private String end;

    @NotBlank
    @Schema(example = "Europe/Berlin")
    private String zoneId;
}
