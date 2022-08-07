package com.ivanfranchin.jpaassociations.onetoone.sharedpk.repository;

import com.ivanfranchin.jpaassociations.onetoone.sharedpk.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
