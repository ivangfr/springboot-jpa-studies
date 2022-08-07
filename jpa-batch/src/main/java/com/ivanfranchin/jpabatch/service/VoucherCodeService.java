package com.ivanfranchin.jpabatch.service;

import com.ivanfranchin.jpabatch.model.Partner;
import com.ivanfranchin.jpabatch.model.VoucherCode;

import java.util.List;
import java.util.stream.Stream;

public interface VoucherCodeService {

    Stream<VoucherCode> getStreamOfVoucherCodesByPartner(Partner partner);

    List<VoucherCode> getListOfVoucherCodesByPartner(Partner partner);

    List<VoucherCode> saveVoucherCodes(List<VoucherCode> voucherCodes);

    void deleteVoucherCodes(List<VoucherCode> voucherCodes);
}
