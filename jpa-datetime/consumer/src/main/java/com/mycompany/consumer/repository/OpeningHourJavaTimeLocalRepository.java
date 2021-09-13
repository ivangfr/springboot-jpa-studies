package com.mycompany.consumer.repository;

import com.mycompany.consumer.model.OpeningHourJavaTimeLocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHourJavaTimeLocalRepository extends JpaRepository<OpeningHourJavaTimeLocal, Long> {
}
