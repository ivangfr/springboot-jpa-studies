package com.ivanfranchin.jpabatch.voucher;

import com.ivanfranchin.jpabatch.partner.Partner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.partition;

@RequiredArgsConstructor
@Service
public class VoucherCodeServiceImpl implements VoucherCodeService {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    private final VoucherCodeRepository voucherCodeRepository;
    private final VoucherCodeBatchProcessing voucherCodeBatchProcessing;

    @Override
    public Stream<VoucherCode> getStreamOfVoucherCodesByPartner(Partner partner) {
        return voucherCodeRepository.streamByPartner(partner);
    }

    @Override
    public List<VoucherCode> getListOfVoucherCodesByPartner(Partner partner) {
        return voucherCodeRepository.findByPartner(partner);
    }

    @Transactional
    @Override
    public List<VoucherCode> saveVoucherCodes(List<VoucherCode> voucherCodes) {
//        return voucherCodeBatchProcessing.saveInBatch(voucherCodes);
        return voucherCodeRepository.saveAll(voucherCodes);
    }

    @Transactional
    @Override
    public void deleteVoucherCodes(List<VoucherCode> voucherCodes) {
        for (List<VoucherCode> subList : partition(voucherCodes, batchSize)) {
            voucherCodeRepository.deleteAllInBatch(subList);
        }
    }
}
