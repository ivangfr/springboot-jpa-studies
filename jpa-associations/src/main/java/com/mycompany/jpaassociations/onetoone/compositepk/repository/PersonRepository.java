package com.mycompany.jpaassociations.onetoone.compositepk.repository;

import com.mycompany.jpaassociations.onetoone.compositepk.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
