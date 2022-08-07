package com.ivanfranchin.jpabatch.service;

import com.ivanfranchin.jpabatch.model.VoucherCode;

import java.util.List;

public interface VoucherCodeBatchProcessing {

    List<VoucherCode> saveInBatch(List<VoucherCode> voucherCodes);
}
