package com.ivanfranchin.consumer.repository;

import com.ivanfranchin.consumer.model.OpeningHourJavaSql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHourJavaSqlRepository extends JpaRepository<OpeningHourJavaSql, Long> {
}
