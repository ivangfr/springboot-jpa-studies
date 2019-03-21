package com.mycompany.jpalocking.repository;

import com.mycompany.jpalocking.model.Life;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LifeRepository extends JpaRepository<Life, Long> {

    int countByPlayerIdIsNull();

    List<Life> findByPlayerIdIsNull();

    Life findFirstByPlayerIdIsNull();

}
