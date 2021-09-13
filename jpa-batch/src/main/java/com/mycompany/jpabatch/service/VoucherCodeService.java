package com.mycompany.jpabatch.service;

import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.model.VoucherCode;

import java.util.List;
import java.util.stream.Stream;

public interface VoucherCodeService {

    Stream<VoucherCode> getStreamOfVoucherCodesByPartner(Partner partner);

    List<VoucherCode> getListOfVoucherCodesByPartner(Partner partner);

    List<VoucherCode> saveVoucherCodes(List<VoucherCode> voucherCodes);

    void deleteVoucherCodes(List<VoucherCode> voucherCodes);
}
