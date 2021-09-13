package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Reviewer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewerRepository extends CrudRepository<Reviewer, Long> {
}
