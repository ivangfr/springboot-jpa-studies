package com.ivanfranchin.jpalocking.repository;

import com.ivanfranchin.jpalocking.model.Life;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LifeRepository extends JpaRepository<Life, Long> {

    @Query("SELECT count(l) from Life l WHERE l.player is null")
    int countByPlayerIdIsNull();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Life> findFirstByPlayerIdIsNull();
}
