package com.mycompany.jpaassociations.onetoone.sharedpk.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
import com.mycompany.jpaassociations.onetoone.sharedpk.model.Person;
import com.mycompany.jpaassociations.onetoone.sharedpk.model.PersonDetail;
import com.mycompany.jpaassociations.onetoone.sharedpk.repository.PersonRepository;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.CreatePersonDetailDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.CreatePersonDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.PersonDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.UpdatePersonDetailDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.UpdatePersonDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PersonDetailControllerTest extends AbstractTestcontainers {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testGetPerson() {
        Person person = personRepository.save(getDefaultPerson());

        String url = String.format(API_PERSONS_PERSON_ID_URL, person.getId());
        ResponseEntity<PersonDto> responseEntity = testRestTemplate.getForEntity(url, PersonDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(person.getId(), responseEntity.getBody().getId());
        assertEquals(person.getName(), responseEntity.getBody().getName());
        assertNull(responseEntity.getBody().getPersonDetail());
    }

    @Test
    void testCreatePerson() {
        CreatePersonDto createPersonDto = getDefaultCreatePersonDto();
        ResponseEntity<PersonDto> responseEntity = testRestTemplate.postForEntity(API_PERSONS_URL, createPersonDto, PersonDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createPersonDto.getName(), responseEntity.getBody().getName());
        assertNull(responseEntity.getBody().getPersonDetail());

        Optional<Person> personOptional = personRepository.findById(responseEntity.getBody().getId());
        assertTrue(personOptional.isPresent());
        personOptional.ifPresent(p -> assertEquals(createPersonDto.getName(), p.getName()));
    }

    @Test
    void testUpdatePerson() {
        Person person = personRepository.save(getDefaultPerson());
        UpdatePersonDto updatePersonDto = getDefaultUpdatePersonDto();

        HttpEntity<UpdatePersonDto> requestUpdate = new HttpEntity<>(updatePersonDto);
        String url = String.format(API_PERSONS_PERSON_ID_URL, person.getId());
        ResponseEntity<PersonDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, PersonDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatePersonDto.getName(), responseEntity.getBody().getName());

        Optional<Person> personOptional = personRepository.findById(person.getId());
        assertTrue(personOptional.isPresent());
        personOptional.ifPresent(p -> assertEquals(updatePersonDto.getName(), p.getName()));
    }

    @Test
    void testDeletePerson() {
        Person person = personRepository.save(getDefaultPerson());

        String url = String.format(API_PERSONS_PERSON_ID_URL, person.getId());
        ResponseEntity<PersonDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, PersonDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(person.getId(), responseEntity.getBody().getId());
        assertEquals(person.getName(), responseEntity.getBody().getName());
        assertNull(responseEntity.getBody().getPersonDetail());

        Optional<Person> personOptional = personRepository.findById(person.getId());
        assertFalse(personOptional.isPresent());
    }

    @Test
    void testAddPersonDetail() {
        Person person = personRepository.save(getDefaultPerson());
        CreatePersonDetailDto createPersonDetailDto = getDefaultCreatePersonDetailDto();

        String url = String.format(API_PERSONS_PERSON_ID_PERSON_DETAILS_URL, person.getId());
        ResponseEntity<PersonDto> responseEntity = testRestTemplate.postForEntity(url, createPersonDetailDto, PersonDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getPersonDetail());
        assertEquals(person.getId(), responseEntity.getBody().getPersonDetail().getId());
        assertEquals(createPersonDetailDto.getDescription(), responseEntity.getBody().getPersonDetail().getDescription());

        Optional<Person> personOptional = personRepository.findById(responseEntity.getBody().getId());
        assertTrue(personOptional.isPresent());
        personOptional.ifPresent(p -> {
            assertEquals(person.getId(), p.getPersonDetail().getId());
            assertEquals(createPersonDetailDto.getDescription(), p.getPersonDetail().getDescription());
        });
    }

    @Test
    void testUpdatePersonDetail() {
        Person person = getDefaultPerson();
        person.addPersonDetail(getDefaultPersonDetail());
        person = personRepository.save(person);

        UpdatePersonDetailDto updatePersonDetailDto = getDefaultUpdatePersonDetailDto();

        HttpEntity<UpdatePersonDetailDto> requestUpdate = new HttpEntity<>(updatePersonDetailDto);
        String url = String.format(API_PERSONS_PERSON_ID_PERSON_DETAILS_URL, person.getId());
        ResponseEntity<PersonDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, PersonDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getPersonDetail());
        assertEquals(updatePersonDetailDto.getDescription(), responseEntity.getBody().getPersonDetail().getDescription());

        Optional<Person> personOptional = personRepository.findById(person.getId());
        assertTrue(personOptional.isPresent());
        personOptional.ifPresent(p -> {
            assertNotNull(p.getPersonDetail());
            assertEquals(updatePersonDetailDto.getDescription(), p.getPersonDetail().getDescription());
        });
    }

    @Disabled
    // Hibernate doesn't allow to delete the person-details
    // WARN [jpa-associations,d3b5b66bb91df6da,d3b5b66bb91df6da,true] 2346 --- [nio-8080-exec-6] o.h.p.entity.AbstractEntityPersister     : HHH000502: The [person] property of the [com.mycompany.jpaassociations.onetoone.sharedpk.model.PersonDetail] entity was modified, but it won't be updated because the property is immutable.
    @Test
    void testDeletePersonDetail() {
        Person person = getDefaultPerson();
        PersonDetail personDetail = getDefaultPersonDetail();
        person.addPersonDetail(personDetail);
        person = personRepository.save(person);

        String url = String.format(API_PERSONS_PERSON_ID_PERSON_DETAILS_URL, person.getId());
        ResponseEntity<PersonDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, PersonDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNull(responseEntity.getBody().getPersonDetail());

        Optional<Person> personOptional = personRepository.findById(person.getId());
        assertTrue(personOptional.isPresent());
        personOptional.ifPresent(p -> assertNull(p.getPersonDetail()));
    }

    private Person getDefaultPerson() {
        Person person = new Person();
        person.setName("Ivan Franchin");
        return person;
    }

    private CreatePersonDto getDefaultCreatePersonDto() {
        CreatePersonDto createPersonDto = new CreatePersonDto();
        createPersonDto.setName("Ivan Franchin");
        return createPersonDto;
    }

    private UpdatePersonDto getDefaultUpdatePersonDto() {
        UpdatePersonDto updatePersonDto = new UpdatePersonDto();
        updatePersonDto.setName("Ivan Franchin 2");
        return updatePersonDto;
    }

    private PersonDetail getDefaultPersonDetail() {
        PersonDetail personDetail = new PersonDetail();
        personDetail.setDescription("More information about the person");
        return personDetail;
    }

    private CreatePersonDetailDto getDefaultCreatePersonDetailDto() {
        CreatePersonDetailDto createPersonDetailDto = new CreatePersonDetailDto();
        createPersonDetailDto.setDescription("More information about the person");
        return createPersonDetailDto;
    }

    private UpdatePersonDetailDto getDefaultUpdatePersonDetailDto() {
        UpdatePersonDetailDto updatePersonDetailDto = new UpdatePersonDetailDto();
        updatePersonDetailDto.setDescription("New information about the person");
        return updatePersonDetailDto;
    }

    private static final String API_PERSONS_URL = "/api/persons";
    private static final String API_PERSONS_PERSON_ID_URL = "/api/persons/%s";
    private static final String API_PERSONS_PERSON_ID_PERSON_DETAILS_URL = "/api/persons/%s/person-details";

}