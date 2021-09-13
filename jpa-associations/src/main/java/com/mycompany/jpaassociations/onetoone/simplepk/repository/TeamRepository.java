package com.mycompany.jpaassociations.onetoone.simplepk.repository;

import com.mycompany.jpaassociations.onetoone.simplepk.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
}
