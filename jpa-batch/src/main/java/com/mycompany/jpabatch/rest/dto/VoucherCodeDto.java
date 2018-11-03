package com.mycompany.jpabatch.rest.dto;

import lombok.Data;

@Data
public class VoucherCodeDto {

    private Long id;
    private String code;
    private Boolean deleted;

}
