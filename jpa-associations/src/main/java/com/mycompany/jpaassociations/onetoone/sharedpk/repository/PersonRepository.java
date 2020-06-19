package com.mycompany.jpaassociations.onetoone.sharedpk.repository;

import com.mycompany.jpaassociations.onetoone.sharedpk.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
