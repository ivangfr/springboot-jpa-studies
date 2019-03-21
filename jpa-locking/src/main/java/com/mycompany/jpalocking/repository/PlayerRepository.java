package com.mycompany.jpalocking.repository;

import com.mycompany.jpalocking.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
