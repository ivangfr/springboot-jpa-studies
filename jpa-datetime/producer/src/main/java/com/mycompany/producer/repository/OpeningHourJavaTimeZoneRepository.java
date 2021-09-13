package com.mycompany.producer.repository;

import com.mycompany.producer.model.OpeningHourJavaTimeZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHourJavaTimeZoneRepository extends JpaRepository<OpeningHourJavaTimeZone, Long> {
}
