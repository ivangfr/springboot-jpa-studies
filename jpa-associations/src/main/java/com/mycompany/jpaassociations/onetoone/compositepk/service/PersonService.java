package com.mycompany.jpaassociations.onetoone.compositepk.service;

import com.mycompany.jpaassociations.onetoone.compositepk.model.Person;

public interface PersonService {

    Person validateAndGetPerson(Long id);

    Person savePerson(Person person);

    void deletePerson(Person person);

}
