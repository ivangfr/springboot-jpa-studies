package com.mycompany.jpaassociations.manytomany.simplerelationship.rest;

import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Book;
import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Writer;
import com.mycompany.jpaassociations.manytomany.simplerelationship.repository.BookRepository;
import com.mycompany.jpaassociations.manytomany.simplerelationship.repository.WriterRepository;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.BookDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.CreateBookDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.CreateWriterDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateBookDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateWriterDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.WriterDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class WriterBookControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testGetWriter() {
        Writer writer = getDefaultWriter();
        writer = writerRepository.save(writer);

        String url = String.format("/api/writers/%s", writer.getId());
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.getForEntity(url, WriterDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(writer.getId(), responseEntity.getBody().getId());
        assertEquals(writer.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getBooks().size());
    }

    @Test
    void testCreateWriter() {
        CreateWriterDto createWriterDto = getDefaultCreateWriterDto();
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.postForEntity("/api/writers", createWriterDto, WriterDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createWriterDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getBooks().size());

        Optional<Writer> optionalWriter = writerRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalWriter.isPresent());
        assertEquals(createWriterDto.getName(), optionalWriter.get().getName());
    }

    @Test
    void testUpdateWriter() {
        Writer writer = getDefaultWriter();
        writer = writerRepository.save(writer);

        UpdateWriterDto updateWriterDto = getDefaultUpdateWriterDto();

        HttpEntity<UpdateWriterDto> requestUpdate = new HttpEntity<>(updateWriterDto);
        String url = String.format("/api/writers/%s", writer.getId());
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, WriterDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updateWriterDto.getName(), responseEntity.getBody().getName());

        Optional<Writer> optionalWriter = writerRepository.findById(writer.getId());
        assertTrue(optionalWriter.isPresent());
        assertEquals(updateWriterDto.getName(), optionalWriter.get().getName());
    }

    @Test
    void testDeleteWriter() {
        Writer writer = getDefaultWriter();
        writer = writerRepository.save(writer);

        String url = String.format("/api/writers/%s", writer.getId());
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, WriterDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(writer.getId(), responseEntity.getBody().getId());
        assertEquals(writer.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getBooks().size());

        Optional<Writer> optionalWriter = writerRepository.findById(writer.getId());
        assertFalse(optionalWriter.isPresent());
    }

    @Test
    void testAddWriterBook() {
        Writer writer = getDefaultWriter();
        writer = writerRepository.save(writer);

        Book book = getDefaultBook();
        book = bookRepository.save(book);

        String url = String.format("/api/writers/%s/books/%s", writer.getId(), book.getId());
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.postForEntity(url, null, WriterDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(writer.getId(), responseEntity.getBody().getId());
        assertEquals(writer.getName(), responseEntity.getBody().getName());
        assertEquals(1, responseEntity.getBody().getBooks().size());
        assertEquals(book.getId(), responseEntity.getBody().getBooks().get(0).getId());

        Optional<Writer> optionalWriter = writerRepository.findById(writer.getId());
        assertTrue(optionalWriter.isPresent());
        assertEquals(1, optionalWriter.get().getBooks().size());
        assertTrue(optionalWriter.get().getBooks().contains(book));

        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        assertTrue(optionalBook.isPresent());
        assertEquals(1, optionalBook.get().getWriters().size());
        assertTrue(optionalBook.get().getWriters().contains(writer));
    }

    @Test
    void testRemoveWriterBook() {
        Book book = getDefaultBook();
        book = bookRepository.save(book);

        Writer writer = getDefaultWriter();
        writer.addBook(book);
        writer = writerRepository.save(writer);

        String url = String.format("/api/writers/%s/books/%s", writer.getId(), book.getId());
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, WriterDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(writer.getId(), responseEntity.getBody().getId());
        assertEquals(writer.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getBooks().size());

        Optional<Writer> optionalWriter = writerRepository.findById(writer.getId());
        assertTrue(optionalWriter.isPresent());
        assertEquals(0, optionalWriter.get().getBooks().size());

        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        assertTrue(optionalBook.isPresent());
        assertEquals(0, optionalBook.get().getWriters().size());
    }

    @Test
    void testGetBook() {
        Book book = getDefaultBook();
        book = bookRepository.save(book);

        String url = String.format("/api/books/%s", book.getId());
        ResponseEntity<BookDto> responseEntity = testRestTemplate.getForEntity(url, BookDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(book.getId(), responseEntity.getBody().getId());
        assertEquals(book.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWriters().size());
    }

    @Test
    void testCreateBook() {
        CreateBookDto createBookDto = getDefaultCreateBookDto();
        ResponseEntity<BookDto> responseEntity = testRestTemplate.postForEntity("/api/books", createBookDto, BookDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createBookDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWriters().size());

        Optional<Book> optionalBook = bookRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalBook.isPresent());
        assertEquals(createBookDto.getName(), optionalBook.get().getName());
    }

    @Test
    void testUpdateBook() {
        Book book = getDefaultBook();
        book = bookRepository.save(book);

        UpdateBookDto updateBookDto = getDefaultUpdateBookDto();

        HttpEntity<UpdateBookDto> requestUpdate = new HttpEntity<>(updateBookDto);
        String url = String.format("/api/books/%s", book.getId());
        ResponseEntity<BookDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, BookDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updateBookDto.getName(), responseEntity.getBody().getName());

        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        assertTrue(optionalBook.isPresent());
        assertEquals(updateBookDto.getName(), optionalBook.get().getName());
    }

    @Test
    void testDeleteBook() {
        Book book = getDefaultBook();
        book = bookRepository.save(book);

        String url = String.format("/api/books/%s", book.getId());
        ResponseEntity<BookDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, BookDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(book.getId(), responseEntity.getBody().getId());
        assertEquals(book.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWriters().size());

        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        assertFalse(optionalBook.isPresent());
    }

    @Test
    void testAddBookWriter() {
        Book book = getDefaultBook();
        book = bookRepository.save(book);

        Writer writer = getDefaultWriter();
        writer = writerRepository.save(writer);

        String url = String.format("/api/books/%s/writers/%s", book.getId(), writer.getId());
        ResponseEntity<BookDto> responseEntity = testRestTemplate.postForEntity(url, null, BookDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(book.getId(), responseEntity.getBody().getId());
        assertEquals(book.getName(), responseEntity.getBody().getName());
        assertEquals(1, responseEntity.getBody().getWriters().size());
        assertEquals(writer.getId(), responseEntity.getBody().getWriters().get(0).getId());

        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        assertTrue(optionalBook.isPresent());
        assertEquals(1, optionalBook.get().getWriters().size());
        assertTrue(optionalBook.get().getWriters().contains(writer));

        Optional<Writer> optionalWriter = writerRepository.findById(writer.getId());
        assertTrue(optionalWriter.isPresent());
        assertEquals(1, optionalWriter.get().getBooks().size());
        assertTrue(optionalWriter.get().getBooks().contains(book));
    }

    @Test
    void testRemoveBookWriter() {
        Book book = getDefaultBook();
        book = bookRepository.save(book);

        Writer writer = getDefaultWriter();
        writer = writerRepository.save(writer);

        String url = String.format("/api/books/%s/writers/%s", book.getId(), writer.getId());
        ResponseEntity<BookDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, BookDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(book.getId(), responseEntity.getBody().getId());
        assertEquals(book.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWriters().size());

        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        assertTrue(optionalBook.isPresent());
        assertEquals(0, optionalBook.get().getWriters().size());

        Optional<Writer> optionalWriter = writerRepository.findById(writer.getId());
        assertTrue(optionalWriter.isPresent());
        assertEquals(0, optionalWriter.get().getBooks().size());
    }

    private Writer getDefaultWriter() {
        Writer writer = new Writer();
        writer.setName("Ivan Franchin");
        return writer;
    }

    private CreateWriterDto getDefaultCreateWriterDto() {
        CreateWriterDto createWriterDto = new CreateWriterDto();
        createWriterDto.setName("Ivan Franchin");
        return createWriterDto;
    }

    private UpdateWriterDto getDefaultUpdateWriterDto() {
        UpdateWriterDto updateWriterDto = new UpdateWriterDto();
        updateWriterDto.setName("Steve Jobs");
        return updateWriterDto;
    }

    private Book getDefaultBook() {
        Book book = new Book();
        book.setName("Introduction to Java 8");
        return book;
    }

    private CreateBookDto getDefaultCreateBookDto() {
        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setName("Introduction to Java 8");
        return createBookDto;
    }

    private UpdateBookDto getDefaultUpdateBookDto() {
        UpdateBookDto updateBookDto = new UpdateBookDto();
        updateBookDto.setName("Introduction to Java 8");
        return updateBookDto;
    }

}