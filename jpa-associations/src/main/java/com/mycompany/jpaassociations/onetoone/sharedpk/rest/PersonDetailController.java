package com.mycompany.jpaassociations.onetoone.sharedpk.rest;

import com.mycompany.jpaassociations.onetoone.sharedpk.mapper.PersonMapper;
import com.mycompany.jpaassociations.onetoone.sharedpk.model.Person;
import com.mycompany.jpaassociations.onetoone.sharedpk.model.PersonDetail;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.CreatePersonDetailDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.CreatePersonDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.PersonDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.UpdatePersonDetailDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.UpdatePersonDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/persons")
public class PersonDetailController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @GetMapping("/{personId}")
    public PersonDto getPerson(@PathVariable Long personId) {
        Person person = personService.validateAndGetPerson(personId);
        return personMapper.toPersonDto(person);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PersonDto createPerson(@Valid @RequestBody CreatePersonDto createPersonDto) {
        Person person = personMapper.toPerson(createPersonDto);
        person = personService.savePerson(person);
        return personMapper.toPersonDto(person);
    }

    @PutMapping("/{personId}")
    public PersonDto updatePerson(@PathVariable Long personId, @Valid @RequestBody UpdatePersonDto updatePersonDto) {
        Person person = personService.validateAndGetPerson(personId);
        personMapper.updatePersonFromDto(updatePersonDto, person);
        person = personService.savePerson(person);
        return personMapper.toPersonDto(person);
    }

    @DeleteMapping("/{personId}")
    public PersonDto deletePerson(@PathVariable Long personId) {
        Person person = personService.validateAndGetPerson(personId);
        personService.deletePerson(person);
        return personMapper.toPersonDto(person);
    }

    @PostMapping("/{personId}/person-details")
    public PersonDto addPersonDetail(@PathVariable Long personId, @Valid @RequestBody CreatePersonDetailDto createPersonDetailDto) {
        Person person = personService.validateAndGetPerson(personId);
        PersonDetail personDetail = personMapper.toPersonDetail(createPersonDetailDto);
        person.addPersonDetail(personDetail);
        person = personService.savePerson(person);
        return personMapper.toPersonDto(person);
    }

    @PutMapping("/{personId}/person-details")
    public PersonDto updatePersonDetail(@PathVariable Long personId, @Valid @RequestBody UpdatePersonDetailDto updatePersonDetailDto) {
        Person person = personService.validateAndGetPerson(personId);
        PersonDetail personDetail = person.getPersonDetail();
        personMapper.updatePersonDetailFromDto(updatePersonDetailDto, personDetail);
        person = personService.savePerson(person);
        return personMapper.toPersonDto(person);
    }

    // Hibernate doesn't allow to delete the person-details
    // WARN [jpa-associations,d3b5b66bb91df6da,d3b5b66bb91df6da,true] 2346 --- [nio-8080-exec-6] o.h.p.entity.AbstractEntityPersister     : HHH000502: The [person] property of the [com.mycompany.jpaassociations.onetoone.sharedpk.model.PersonDetail] entity was modified, but it won't be updated because the property is immutable.
    @DeleteMapping("/{personId}/person-details")
    public PersonDto deletePersonDetail(@PathVariable Long personId) {
        Person person = personService.validateAndGetPerson(personId);
        person.removePersonDetail();
        person = personService.savePerson(person);
        return personMapper.toPersonDto(person);
    }

}
