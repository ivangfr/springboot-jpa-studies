package com.mycompany.jpaassociations.manytomany.simplerelationship.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class WriterBookControllerTest extends AbstractTestcontainers {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testGetWriter() {
        Writer writer = writerRepository.save(getDefaultWriter());

        String url = String.format(API_WRITERS_WRITER_ID_URL, writer.getId());
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
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.postForEntity(API_WRITERS_URL, createWriterDto, WriterDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createWriterDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getBooks().size());

        Optional<Writer> writerOptional = writerRepository.findById(responseEntity.getBody().getId());
        assertTrue(writerOptional.isPresent());
        writerOptional.ifPresent(w -> assertEquals(createWriterDto.getName(), w.getName()));
    }

    @Test
    void testUpdateWriter() {
        Writer writer = writerRepository.save(getDefaultWriter());
        UpdateWriterDto updateWriterDto = getDefaultUpdateWriterDto();

        HttpEntity<UpdateWriterDto> requestUpdate = new HttpEntity<>(updateWriterDto);
        String url = String.format(API_WRITERS_WRITER_ID_URL, writer.getId());
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, WriterDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updateWriterDto.getName(), responseEntity.getBody().getName());

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertTrue(writerOptional.isPresent());
        writerOptional.ifPresent(w -> assertEquals(updateWriterDto.getName(), w.getName()));
    }

    @Test
    void testDeleteWriter() {
        Writer writer = writerRepository.save(getDefaultWriter());

        String url = String.format(API_WRITERS_WRITER_ID_URL, writer.getId());
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, WriterDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(writer.getId(), responseEntity.getBody().getId());
        assertEquals(writer.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getBooks().size());

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertFalse(writerOptional.isPresent());
    }

    @Test
    void testAddWriterBook() {
        final Writer writer = writerRepository.save(getDefaultWriter());
        final Book book = bookRepository.save(getDefaultBook());

        String url = String.format(API_WRITERS_WRITER_ID_BOOKS_BOOK_ID_URL, writer.getId(), book.getId());
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.postForEntity(url, null, WriterDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(writer.getId(), responseEntity.getBody().getId());
        assertEquals(writer.getName(), responseEntity.getBody().getName());
        assertEquals(1, responseEntity.getBody().getBooks().size());
        assertEquals(book.getId(), responseEntity.getBody().getBooks().get(0).getId());

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertTrue(writerOptional.isPresent());
        writerOptional.ifPresent(w -> {
            assertEquals(1, w.getBooks().size());
            assertTrue(w.getBooks().contains(book));
        });

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertTrue(bookOptional.isPresent());
        bookOptional.ifPresent(b -> {
            assertEquals(1, b.getWriters().size());
            assertTrue(b.getWriters().contains(writer));
        });
    }

    @Test
    void testRemoveWriterBook() {
        Book book = bookRepository.save(getDefaultBook());

        Writer writer = getDefaultWriter();
        writer.addBook(book);
        writer = writerRepository.save(writer);

        String url = String.format(API_WRITERS_WRITER_ID_BOOKS_BOOK_ID_URL, writer.getId(), book.getId());
        ResponseEntity<WriterDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, WriterDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(writer.getId(), responseEntity.getBody().getId());
        assertEquals(writer.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getBooks().size());

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertTrue(writerOptional.isPresent());
        writerOptional.ifPresent(w -> assertEquals(0, w.getBooks().size()));

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertTrue(bookOptional.isPresent());
        bookOptional.ifPresent(b -> assertEquals(0, b.getWriters().size()));
    }

    @Test
    void testGetBook() {
        Book book = bookRepository.save(getDefaultBook());

        String url = String.format(API_BOOKS_BOOK_ID_URL, book.getId());
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
        ResponseEntity<BookDto> responseEntity = testRestTemplate.postForEntity(API_BOOKS_URL, createBookDto, BookDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createBookDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWriters().size());

        Optional<Book> bookOptional = bookRepository.findById(responseEntity.getBody().getId());
        assertTrue(bookOptional.isPresent());
        bookOptional.ifPresent(b -> assertEquals(createBookDto.getName(), b.getName()));
    }

    @Test
    void testUpdateBook() {
        Book book = bookRepository.save(getDefaultBook());
        UpdateBookDto updateBookDto = getDefaultUpdateBookDto();

        HttpEntity<UpdateBookDto> requestUpdate = new HttpEntity<>(updateBookDto);
        String url = String.format(API_BOOKS_BOOK_ID_URL, book.getId());
        ResponseEntity<BookDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, BookDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updateBookDto.getName(), responseEntity.getBody().getName());

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertTrue(bookOptional.isPresent());
        bookOptional.ifPresent(b -> assertEquals(updateBookDto.getName(), b.getName()));
    }

    @Test
    void testDeleteBook() {
        Book book = bookRepository.save(getDefaultBook());

        String url = String.format(API_BOOKS_BOOK_ID_URL, book.getId());
        ResponseEntity<BookDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, BookDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(book.getId(), responseEntity.getBody().getId());
        assertEquals(book.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWriters().size());

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertFalse(bookOptional.isPresent());
    }

    @Test
    void testAddBookWriter() {
        Book book = bookRepository.save(getDefaultBook());
        Writer writer = writerRepository.save(getDefaultWriter());

        String url = String.format(API_BOOKS_BOOK_ID_WRITERS_WRITER_ID_URL, book.getId(), writer.getId());
        ResponseEntity<BookDto> responseEntity = testRestTemplate.postForEntity(url, null, BookDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(book.getId(), responseEntity.getBody().getId());
        assertEquals(book.getName(), responseEntity.getBody().getName());
        assertEquals(1, responseEntity.getBody().getWriters().size());
        assertEquals(writer.getId(), responseEntity.getBody().getWriters().get(0).getId());

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertTrue(bookOptional.isPresent());
        bookOptional.ifPresent(b -> {
            assertEquals(1, b.getWriters().size());
            assertTrue(b.getWriters().contains(writer));
        });

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertTrue(writerOptional.isPresent());
        writerOptional.ifPresent(w -> {
            assertEquals(1, w.getBooks().size());
            assertTrue(w.getBooks().contains(book));
        });
    }

    @Test
    void testRemoveBookWriter() {
        Book book = bookRepository.save(getDefaultBook());
        Writer writer = writerRepository.save(getDefaultWriter());

        String url = String.format(API_BOOKS_BOOK_ID_WRITERS_WRITER_ID_URL, book.getId(), writer.getId());
        ResponseEntity<BookDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, BookDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(book.getId(), responseEntity.getBody().getId());
        assertEquals(book.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWriters().size());

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertTrue(bookOptional.isPresent());
        bookOptional.ifPresent(b -> assertEquals(0, b.getWriters().size()));

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertTrue(writerOptional.isPresent());
        writerOptional.ifPresent(w -> assertEquals(0, w.getBooks().size()));
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
        updateBookDto.setName("Introduction to Java 11");
        return updateBookDto;
    }

    private static final String API_WRITERS_URL = "/api/writers";
    private static final String API_WRITERS_WRITER_ID_URL = "/api/writers/%s";
    private static final String API_BOOKS_URL = "/api/books";
    private static final String API_BOOKS_BOOK_ID_URL = "/api/books/%s";
    private static final String API_WRITERS_WRITER_ID_BOOKS_BOOK_ID_URL = "/api/writers/%s/books/%s";
    private static final String API_BOOKS_BOOK_ID_WRITERS_WRITER_ID_URL = "/api/books/%s/writers/%s";

}