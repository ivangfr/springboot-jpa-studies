package com.mycompany.jpaassociations.onetoone.sharedpk.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
import com.mycompany.jpaassociations.onetoone.sharedpk.model.Person;
import com.mycompany.jpaassociations.onetoone.sharedpk.model.PersonDetail;
import com.mycompany.jpaassociations.onetoone.sharedpk.repository.PersonRepository;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.CreatePersonDetailRequest;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.CreatePersonRequest;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.PersonResponse;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.UpdatePersonDetailRequest;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.UpdatePersonRequest;
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

import static org.assertj.core.api.Assertions.assertThat;

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
        ResponseEntity<PersonResponse> responseEntity = testRestTemplate.getForEntity(url, PersonResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(person.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(person.getName());
        assertThat(responseEntity.getBody().getPersonDetail()).isNull();
    }

    @Test
    void testCreatePerson() {
        CreatePersonRequest createPersonRequest = new CreatePersonRequest("Ivan Franchin");
        ResponseEntity<PersonResponse> responseEntity = testRestTemplate.postForEntity(
                API_PERSONS_URL, createPersonRequest, PersonResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(createPersonRequest.getName());
        assertThat(responseEntity.getBody().getPersonDetail()).isNull();

        Optional<Person> personOptional = personRepository.findById(responseEntity.getBody().getId());
        assertThat(personOptional.isPresent()).isTrue();
        personOptional.ifPresent(p -> assertThat(p.getName()).isEqualTo(createPersonRequest.getName()));
    }

    @Test
    void testUpdatePerson() {
        Person person = personRepository.save(getDefaultPerson());
        UpdatePersonRequest updatePersonRequest = new UpdatePersonRequest("Ivan Franchin 2");

        HttpEntity<UpdatePersonRequest> requestUpdate = new HttpEntity<>(updatePersonRequest);
        String url = String.format(API_PERSONS_PERSON_ID_URL, person.getId());
        ResponseEntity<PersonResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, requestUpdate, PersonResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(updatePersonRequest.getName());

        Optional<Person> personOptional = personRepository.findById(person.getId());
        assertThat(personOptional.isPresent()).isTrue();
        personOptional.ifPresent(p -> assertThat(p.getName()).isEqualTo(updatePersonRequest.getName()));
    }

    @Test
    void testDeletePerson() {
        Person person = personRepository.save(getDefaultPerson());

        String url = String.format(API_PERSONS_PERSON_ID_URL, person.getId());
        ResponseEntity<PersonResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, PersonResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(person.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(person.getName());
        assertThat(responseEntity.getBody().getPersonDetail()).isNull();

        Optional<Person> personOptional = personRepository.findById(person.getId());
        assertThat(personOptional.isPresent()).isFalse();
    }

    @Test
    void testAddPersonDetail() {
        Person person = personRepository.save(getDefaultPerson());
        CreatePersonDetailRequest createPersonDetailRequest = new CreatePersonDetailRequest("More information about the person");

        String url = String.format(API_PERSONS_PERSON_ID_PERSON_DETAILS_URL, person.getId());
        ResponseEntity<PersonResponse> responseEntity = testRestTemplate.postForEntity(
                url, createPersonDetailRequest, PersonResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getPersonDetail()).isNotNull();
        assertThat(responseEntity.getBody().getPersonDetail().getId()).isEqualTo(person.getId());
        assertThat(responseEntity.getBody().getPersonDetail().getDescription())
                .isEqualTo(createPersonDetailRequest.getDescription());

        Optional<Person> personOptional = personRepository.findById(responseEntity.getBody().getId());
        assertThat(personOptional.isPresent()).isTrue();
        personOptional.ifPresent(p -> {
            assertThat(p.getPersonDetail().getId()).isEqualTo(person.getId());
            assertThat(p.getPersonDetail().getDescription()).isEqualTo(createPersonDetailRequest.getDescription());
        });
    }

    @Test
    void testUpdatePersonDetail() {
        Person person = getDefaultPerson();
        person.addPersonDetail(getDefaultPersonDetail());
        person = personRepository.save(person);

        UpdatePersonDetailRequest updatePersonDetailRequest = new UpdatePersonDetailRequest("New information about the person");

        HttpEntity<UpdatePersonDetailRequest> requestUpdate = new HttpEntity<>(updatePersonDetailRequest);
        String url = String.format(API_PERSONS_PERSON_ID_PERSON_DETAILS_URL, person.getId());
        ResponseEntity<PersonResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, requestUpdate, PersonResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getPersonDetail()).isNotNull();
        assertThat(responseEntity.getBody().getPersonDetail().getDescription())
                .isEqualTo(updatePersonDetailRequest.getDescription());

        Optional<Person> personOptional = personRepository.findById(person.getId());
        assertThat(personOptional.isPresent()).isTrue();
        personOptional.ifPresent(p -> {
            assertThat(p.getPersonDetail()).isNotNull();
            assertThat(p.getPersonDetail().getDescription()).isEqualTo(updatePersonDetailRequest.getDescription());
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
        ResponseEntity<PersonResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, PersonResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getPersonDetail()).isNull();

        Optional<Person> personOptional = personRepository.findById(person.getId());
        assertThat(personOptional.isPresent()).isTrue();
        personOptional.ifPresent(p -> assertThat(p.getPersonDetail()).isNull());
    }

    private Person getDefaultPerson() {
        Person person = new Person();
        person.setName("Ivan Franchin");
        return person;
    }

    private PersonDetail getDefaultPersonDetail() {
        PersonDetail personDetail = new PersonDetail();
        personDetail.setDescription("More information about the person");
        return personDetail;
    }

    private static final String API_PERSONS_URL = "/api/persons";
    private static final String API_PERSONS_PERSON_ID_URL = "/api/persons/%s";
    private static final String API_PERSONS_PERSON_ID_PERSON_DETAILS_URL = "/api/persons/%s/person-details";

}