package com.ivanfranchin.jpabatch.voucher;

import java.util.List;

/* This interface provides an alternative method for saving voucher codes in batch, rather than using the VoucherCodeRepository.
   In order to use it:
   1. In the VoucherCode class:
   1.1. Uncomment `@GeneratedValue(strategy = GenerationType.IDENTITY)`
   1.2. Comment out `@GeneratedValue(strategy = GenerationType.AUTO)`
   2. In the saveVoucherCodes method of the VoucherCodeServiceImpl class:
   2.1. Uncomment `return voucherCodeBatchProcessing.saveInBatch(voucherCodes);`
   2.2. Comment out `return voucherCodeRepository.saveAll(voucherCodes);`*/
public interface VoucherCodeBatchProcessing {

    List<VoucherCode> saveInBatch(List<VoucherCode> voucherCodes);
}
