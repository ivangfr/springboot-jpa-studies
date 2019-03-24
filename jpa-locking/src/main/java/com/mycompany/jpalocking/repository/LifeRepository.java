package com.mycompany.jpalocking.repository;

import com.mycompany.jpalocking.model.Life;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface LifeRepository extends JpaRepository<Life, Long> {

    @Query("SELECT count(l) from Life l WHERE l.player is null")
    int countByPlayerIdIsNull();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Life> findFirstByPlayerIdIsNull();

}
