package com.ivanfranchin.jpabatch.voucher;

import com.ivanfranchin.jpabatch.partner.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface VoucherCodeRepository extends JpaRepository<VoucherCode, Long> {

    Stream<VoucherCode> streamByPartner(Partner partner);

    List<VoucherCode> findByPartner(Partner partner);
}
