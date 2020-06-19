package com.mycompany.jpabatch.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class CreateVoucherCodeDto {

    @Schema(example = "[\"101\", \"102\", \"103\", \"104\", \"105\", \"106\", \"107\", \"108\", \"109\", \"110\", \"111\", \"112\", \"113\", \"114\", \"115\"]")
    private Set<String> voucherCodes;

}
