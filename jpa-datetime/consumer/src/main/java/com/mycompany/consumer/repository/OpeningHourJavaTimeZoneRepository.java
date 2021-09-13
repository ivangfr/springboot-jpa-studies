package com.mycompany.consumer.repository;

import com.mycompany.consumer.model.OpeningHourJavaTimeZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHourJavaTimeZoneRepository extends JpaRepository<OpeningHourJavaTimeZone, Long> {
}
