package com.mycompany.jpabatch.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class PartnerDto {

    private Long id;
    private String name;
    private List<VoucherCodeDto> voucherCodes;

}
