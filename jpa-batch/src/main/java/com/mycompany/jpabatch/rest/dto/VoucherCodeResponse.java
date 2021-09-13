package com.mycompany.jpabatch.rest.dto;

import lombok.Value;

@Value
public class VoucherCodeResponse {

    Long id;
    String code;
    Boolean deleted;
}
