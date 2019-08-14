package com.mycompany.producer.repository;

import com.mycompany.producer.model.OpeningHourJavaSql;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningHourJavaSqlRepository extends JpaRepository<OpeningHourJavaSql, Long> {
}
