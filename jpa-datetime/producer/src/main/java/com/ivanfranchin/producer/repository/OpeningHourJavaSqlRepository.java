package com.ivanfranchin.producer.repository;

import com.ivanfranchin.producer.model.OpeningHourJavaSql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHourJavaSqlRepository extends JpaRepository<OpeningHourJavaSql, Long> {
}
