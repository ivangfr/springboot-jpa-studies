package com.ivanfranchin.jpabatch.partner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    @Query("SELECT p FROM Partner p LEFT JOIN FETCH p.voucherCodes WHERE p.id = :id")
    Optional<Partner> findByIdWithVoucherCodes(Long id);
}
