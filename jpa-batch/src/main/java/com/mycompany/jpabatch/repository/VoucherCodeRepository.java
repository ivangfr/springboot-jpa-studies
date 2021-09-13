package com.mycompany.jpabatch.repository;

import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.model.VoucherCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface VoucherCodeRepository extends JpaRepository<VoucherCode, Long> {

    Stream<VoucherCode> streamByPartner(Partner partner);

    List<VoucherCode> findByPartner(Partner partner);
}
