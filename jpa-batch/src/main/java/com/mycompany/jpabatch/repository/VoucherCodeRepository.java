package com.mycompany.jpabatch.repository;

import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.model.VoucherCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherCodeRepository extends JpaRepository<VoucherCode, String> {

    List<VoucherCode> findByPartner(Partner partner);

}
