package com.mycompany.jpaassociations.onetoone.compositepk.rest;

import com.mycompany.jpaassociations.MessageError;
import com.mycompany.jpaassociations.onetoone.compositepk.model.Person;
import com.mycompany.jpaassociations.onetoone.compositepk.repository.PersonRepository;
import com.mycompany.jpaassociations.onetoone.compositepk.rest.dto.CreatePersonDetailDto;
import com.mycompany.jpaassociations.onetoone.compositepk.rest.dto.CreatePersonDto;
import com.mycompany.jpaassociations.onetoone.compositepk.rest.dto.PersonDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class PersonDetailControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testGetPerson() {
        Person person = getDefaultPerson();
        person = personRepository.save(person);

        String url = String.format("/api/persons/%s", person.getId());
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
        ResponseEntity<PersonDto> responseEntity = testRestTemplate.postForEntity("/api/persons", createPersonDto, PersonDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createPersonDto.getName(), responseEntity.getBody().getName());
        assertNull(responseEntity.getBody().getPersonDetail());

        Optional<Person> optionalPerson = personRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalPerson.isPresent());
        assertEquals(createPersonDto.getName(), optionalPerson.get().getName());
    }

    @Test
    void testAddPersonDetail() {
        Person person = getDefaultPerson();
        person = personRepository.save(person);

        CreatePersonDetailDto createPersonDetailDto = getDefaultCreatePersonDetailDto();

        String url = String.format("/api/persons/%s/person-details", person.getId());
        ResponseEntity<MessageError> responseEntity = testRestTemplate.postForEntity(url, createPersonDetailDto, MessageError.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getTimestamp());
        assertEquals(500, responseEntity.getBody().getStatus());
        assertEquals("Internal Server Error", responseEntity.getBody().getError());
        assertEquals("No part of a composite identifier may be null; nested exception is org.hibernate.HibernateException: No part of a composite identifier may be null", responseEntity.getBody().getMessage());
        assertEquals(url, responseEntity.getBody().getPath());
        assertNull(responseEntity.getBody().getErrors());
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

    private CreatePersonDetailDto getDefaultCreatePersonDetailDto() {
        CreatePersonDetailDto createPersonDetailDto = new CreatePersonDetailDto();
        createPersonDetailDto.setDescription("More information about the person");
        return createPersonDetailDto;
    }

}