package com.ivanfranchin.jpabatch.voucher;

import java.util.List;

public interface VoucherCodeBatchProcessing {

    List<VoucherCode> saveInBatch(List<VoucherCode> voucherCodes);
}
