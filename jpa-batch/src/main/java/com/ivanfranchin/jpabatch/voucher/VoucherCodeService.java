package com.ivanfranchin.jpabatch.voucher;

import com.ivanfranchin.jpabatch.partner.Partner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class VoucherCodeService {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    private final VoucherCodeRepository voucherCodeRepository;
    private final VoucherCodeBatchProcessing voucherCodeBatchProcessing;

    public Stream<VoucherCode> getStreamOfVoucherCodesByPartner(Partner partner) {
        return voucherCodeRepository.streamByPartner(partner);
    }

    public List<VoucherCode> getListOfVoucherCodesByPartner(Partner partner) {
        return voucherCodeRepository.findByPartner(partner);
    }

    @Transactional
    public List<VoucherCode> saveVoucherCodes(List<VoucherCode> voucherCodes) {
        /* If you want to use VoucherCodeBatchProcessing class instead of VoucherCodeRepository */
        //return voucherCodeBatchProcessing.saveInBatch(voucherCodes);
        return voucherCodeRepository.saveAll(voucherCodes);
    }

    @Transactional
    public void deleteVoucherCodes(List<VoucherCode> voucherCodes) {
        for (int i = 0; i < voucherCodes.size(); i += batchSize) {
            List<VoucherCode> subList = voucherCodes.subList(i, Math.min(i + batchSize, voucherCodes.size()));
            voucherCodeRepository.deleteAllInBatch(subList);
        }
    }
}
