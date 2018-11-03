package com.mycompany.jpabatch.service;

import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.model.VoucherCode;
import com.mycompany.jpabatch.repository.VoucherCodeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.collect.Lists.partition;

@Service
public class VoucherCodeServiceImpl implements VoucherCodeService {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    private final VoucherCodeRepository voucherCodeRepository;

    public VoucherCodeServiceImpl(VoucherCodeRepository voucherCodeRepository) {
        this.voucherCodeRepository = voucherCodeRepository;
    }

    @Override
    public List<VoucherCode> getVoucherCodesByPartnet(Partner partner) {
        return voucherCodeRepository.findByPartner(partner);
    }

    @Transactional
    @Override
    public List<VoucherCode> saveVoucherCodes(List<VoucherCode> voucherCodes) {
        return voucherCodeRepository.saveAll(voucherCodes);
    }

    @Transactional
    @Override
    public void deleteVoucherCodes(List<VoucherCode> voucherCodes) {
        for (List<VoucherCode> subList : partition(voucherCodes, batchSize)) {
            voucherCodeRepository.deleteInBatch(subList);
        }
    }
}
