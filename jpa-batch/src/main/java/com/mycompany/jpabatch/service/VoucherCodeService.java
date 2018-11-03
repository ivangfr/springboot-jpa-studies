package com.mycompany.jpabatch.service;

import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.model.VoucherCode;

import java.util.List;

public interface VoucherCodeService {

    List<VoucherCode> getVoucherCodesByPartnet(Partner partner);

    List<VoucherCode> saveVoucherCodes(List<VoucherCode> voucherCodes);

    void deleteVoucherCodes(List<VoucherCode> voucherCodes);

}
