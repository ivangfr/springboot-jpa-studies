package com.ivanfranchin.producer.repository;

import com.ivanfranchin.producer.model.OpeningHourJavaTimeLocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHourJavaTimeLocalRepository extends JpaRepository<OpeningHourJavaTimeLocal, Long> {
}
