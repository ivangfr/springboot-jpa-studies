package com.mycompany.jpaassociations.onetoone.simplepk.repository;

import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {
}
