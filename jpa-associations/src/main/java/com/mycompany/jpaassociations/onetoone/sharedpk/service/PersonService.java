package com.mycompany.jpaassociations.onetoone.sharedpk.service;

import com.mycompany.jpaassociations.onetoone.sharedpk.model.Person;

public interface PersonService {

    Person validateAndGetPerson(Long id);

    Person savePerson(Person person);

    void deletePerson(Person person);
}
