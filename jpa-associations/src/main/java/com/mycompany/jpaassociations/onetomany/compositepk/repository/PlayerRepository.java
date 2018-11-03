package com.mycompany.jpaassociations.onetomany.compositepk.repository;

import com.mycompany.jpaassociations.onetomany.compositepk.model.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
}
