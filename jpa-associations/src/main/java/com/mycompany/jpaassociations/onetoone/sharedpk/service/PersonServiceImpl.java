package com.mycompany.jpaassociations.onetoone.sharedpk.service;

import com.mycompany.jpaassociations.onetoone.sharedpk.exception.PersonNotFoundException;
import com.mycompany.jpaassociations.onetoone.sharedpk.model.Person;
import com.mycompany.jpaassociations.onetoone.sharedpk.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Person validateAndGetPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(String.format("Person with id '%s' not found", id)));
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
