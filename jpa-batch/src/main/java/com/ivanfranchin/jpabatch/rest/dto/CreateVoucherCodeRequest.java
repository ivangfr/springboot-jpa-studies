package com.ivanfranchin.jpabatch.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record CreateVoucherCodeRequest(
        @Schema(example = "[\"101\", \"102\", \"103\", \"104\", \"105\", \"106\", \"107\", \"108\", \"109\", \"110\", \"111\", \"112\", \"113\", \"114\", \"115\"]") Set<String> voucherCodes) {
}
