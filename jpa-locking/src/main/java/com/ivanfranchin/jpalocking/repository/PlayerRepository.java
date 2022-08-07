package com.ivanfranchin.jpalocking.repository;

import com.ivanfranchin.jpalocking.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
