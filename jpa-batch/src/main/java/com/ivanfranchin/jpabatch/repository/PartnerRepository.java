package com.ivanfranchin.jpabatch.repository;

import com.ivanfranchin.jpabatch.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
}
