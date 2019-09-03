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
    @ApiModelProperty(position = 1, example = "00:00:00")
    private String begin;

    @NotBlank
    @ApiModelProperty(position = 2, example = "23:59:59")
    private String end;

    @NotBlank
    @ApiModelProperty(position = 3, example = "Europe/Berlin")
    private String zoneId;

}
