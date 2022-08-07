package com.ivanfranchin.jpabatch.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVoucherCodeRequest {

    @Schema(example = "[\"101\", \"102\", \"103\", \"104\", \"105\", \"106\", \"107\", \"108\", \"109\", \"110\", \"111\", \"112\", \"113\", \"114\", \"115\"]")
    private Set<String> voucherCodes;
}
