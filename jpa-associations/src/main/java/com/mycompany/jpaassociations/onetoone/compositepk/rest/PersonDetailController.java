package com.mycompany.jpaassociations.onetoone.compositepk.rest;

import com.mycompany.jpaassociations.onetoone.compositepk.model.Person;
import com.mycompany.jpaassociations.onetoone.compositepk.model.PersonDetail;
import com.mycompany.jpaassociations.onetoone.compositepk.rest.dto.CreatePersonDetailDto;
import com.mycompany.jpaassociations.onetoone.compositepk.rest.dto.CreatePersonDto;
import com.mycompany.jpaassociations.onetoone.compositepk.rest.dto.PersonDto;
import com.mycompany.jpaassociations.onetoone.compositepk.service.PersonService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/persons")
public class PersonDetailController {

    private final PersonService personService;
    private final MapperFacade mapperFacade;

    public PersonDetailController(PersonService personService, MapperFacade mapperFacade) {
        this.personService = personService;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping("/{personId}")
    public PersonDto getPerson(@PathVariable Long personId) {
        Person person = personService.validateAndGetPerson(personId);
        return mapperFacade.map(person, PersonDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PersonDto createPerson(@Valid @RequestBody CreatePersonDto createPersonDto) {
        Person person = mapperFacade.map(createPersonDto, Person.class);
        person = personService.savePerson(person);
        return mapperFacade.map(person, PersonDto.class);
    }

    @PostMapping("/{personId}/person-details")
    public PersonDto addPersonDetail(@PathVariable Long personId, @Valid @RequestBody CreatePersonDetailDto createPersonDetailDto) {
        Person person = personService.validateAndGetPerson(personId);
        PersonDetail personDetail = mapperFacade.map(createPersonDetailDto, PersonDetail.class);
        person.addPersonDetail(personDetail);
        person = personService.savePerson(person);
        return mapperFacade.map(person, PersonDto.class);
    }

}
