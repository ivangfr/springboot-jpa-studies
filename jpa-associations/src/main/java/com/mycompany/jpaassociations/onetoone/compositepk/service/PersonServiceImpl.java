package com.mycompany.jpaassociations.onetoone.compositepk.service;

import com.mycompany.jpaassociations.onetoone.compositepk.exception.PersonNotFoundException;
import com.mycompany.jpaassociations.onetoone.compositepk.model.Person;
import com.mycompany.jpaassociations.onetoone.compositepk.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person validateAndGetPerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(String.format("Person with id '%s' not found", id)));
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(Person person) {
        personRepository.delete(person);
    }
}
