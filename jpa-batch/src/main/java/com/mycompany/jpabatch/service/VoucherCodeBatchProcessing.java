package com.mycompany.jpabatch.service;

import com.mycompany.jpabatch.model.VoucherCode;

import java.util.List;

public interface VoucherCodeBatchProcessing {

    List<VoucherCode> saveInBatch(List<VoucherCode> voucherCodes);

}
