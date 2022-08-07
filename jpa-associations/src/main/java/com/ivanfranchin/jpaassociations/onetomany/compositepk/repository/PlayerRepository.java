package com.ivanfranchin.jpaassociations.onetomany.compositepk.repository;

import com.ivanfranchin.jpaassociations.onetomany.compositepk.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
}
