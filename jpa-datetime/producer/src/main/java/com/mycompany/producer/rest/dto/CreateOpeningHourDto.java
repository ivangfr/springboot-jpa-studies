package com.mycompany.producer.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateOpeningHourDto {

    @NotBlank
    @ApiModelProperty(example = "2019-12-31")
    private String date;

    @NotBlank
    @ApiModelProperty(example = "00:00:00", position = 1)
    private String begin;

    @NotBlank
    @ApiModelProperty(example = "23:59:59", position = 2)
    private String end;

    @NotBlank
    @ApiModelProperty(example = "Europe/Berlin", position = 3)
    private String zoneId;

}
