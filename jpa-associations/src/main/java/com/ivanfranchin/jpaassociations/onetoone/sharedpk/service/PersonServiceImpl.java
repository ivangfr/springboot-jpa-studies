package com.ivanfranchin.jpaassociations.onetoone.sharedpk.service;

import com.ivanfranchin.jpaassociations.onetoone.sharedpk.exception.PersonNotFoundException;
import com.ivanfranchin.jpaassociations.onetoone.sharedpk.repository.PersonRepository;
import com.ivanfranchin.jpaassociations.onetoone.sharedpk.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Person validateAndGetPerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
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
