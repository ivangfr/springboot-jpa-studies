package com.ivanfranchin.jpabatch.voucher;

import com.ivanfranchin.jpabatch.partner.Partner;

import java.util.List;
import java.util.stream.Stream;

public interface VoucherCodeService {

    Stream<VoucherCode> getStreamOfVoucherCodesByPartner(Partner partner);

    List<VoucherCode> getListOfVoucherCodesByPartner(Partner partner);

    List<VoucherCode> saveVoucherCodes(List<VoucherCode> voucherCodes);

    void deleteVoucherCodes(List<VoucherCode> voucherCodes);
}
