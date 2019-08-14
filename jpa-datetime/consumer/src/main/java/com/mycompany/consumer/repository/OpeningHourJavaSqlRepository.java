package com.mycompany.consumer.repository;

import com.mycompany.consumer.model.OpeningHourJavaSql;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningHourJavaSqlRepository extends JpaRepository<OpeningHourJavaSql, Long> {
}
