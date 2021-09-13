package com.mycompany.jpaassociations.manytomany.simplerelationship.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Book;
import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Writer;
import com.mycompany.jpaassociations.manytomany.simplerelationship.repository.BookRepository;
import com.mycompany.jpaassociations.manytomany.simplerelationship.repository.WriterRepository;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.BookResponse;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.CreateBookRequest;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.CreateWriterRequest;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateBookRequest;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateWriterRequest;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.WriterResponse;
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
        ResponseEntity<WriterResponse> responseEntity = testRestTemplate.getForEntity(url, WriterResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(writer.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(writer.getName());
        assertThat(responseEntity.getBody().getBooks().size()).isEqualTo(0);
    }

    @Test
    void testCreateWriter() {
        CreateWriterRequest createWriterRequest = new CreateWriterRequest("Ivan Franchin");
        ResponseEntity<WriterResponse> responseEntity = testRestTemplate.postForEntity(
                API_WRITERS_URL, createWriterRequest, WriterResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(createWriterRequest.getName());
        assertThat(responseEntity.getBody().getBooks().size()).isEqualTo(0);

        Optional<Writer> writerOptional = writerRepository.findById(responseEntity.getBody().getId());
        assertThat(writerOptional.isPresent()).isTrue();
        writerOptional.ifPresent(w -> assertThat(w.getName()).isEqualTo(createWriterRequest.getName()));
    }

    @Test
    void testUpdateWriter() {
        Writer writer = writerRepository.save(getDefaultWriter());
        UpdateWriterRequest updateWriterRequest = new UpdateWriterRequest("Steve Jobs");

        HttpEntity<UpdateWriterRequest> requestUpdate = new HttpEntity<>(updateWriterRequest);
        String url = String.format(API_WRITERS_WRITER_ID_URL, writer.getId());
        ResponseEntity<WriterResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, requestUpdate, WriterResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(updateWriterRequest.getName());

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertThat(writerOptional.isPresent()).isTrue();
        writerOptional.ifPresent(w -> assertThat(w.getName()).isEqualTo(updateWriterRequest.getName()));
    }

    @Test
    void testDeleteWriter() {
        Writer writer = writerRepository.save(getDefaultWriter());

        String url = String.format(API_WRITERS_WRITER_ID_URL, writer.getId());
        ResponseEntity<WriterResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, WriterResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(writer.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(writer.getName());
        assertThat(responseEntity.getBody().getBooks().size()).isEqualTo(0);

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertThat(writerOptional.isPresent()).isFalse();
    }

    @Test
    void testAddWriterBook() {
        final Writer writer = writerRepository.save(getDefaultWriter());
        final Book book = bookRepository.save(getDefaultBook());

        String url = String.format(API_WRITERS_WRITER_ID_BOOKS_BOOK_ID_URL, writer.getId(), book.getId());
        ResponseEntity<WriterResponse> responseEntity = testRestTemplate.postForEntity(url, null, WriterResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(writer.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(writer.getName());
        assertThat(responseEntity.getBody().getBooks().size()).isEqualTo(1);
        assertThat(responseEntity.getBody().getBooks().get(0).getId()).isEqualTo(book.getId());

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertThat(writerOptional.isPresent()).isTrue();
        writerOptional.ifPresent(w -> {
            assertThat(w.getBooks().size()).isEqualTo(1);
            assertThat(w.getBooks().contains(book)).isTrue();
        });

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertThat(bookOptional.isPresent()).isTrue();
        bookOptional.ifPresent(b -> {
            assertThat(b.getWriters().size()).isEqualTo(1);
            assertThat(b.getWriters().contains(writer)).isTrue();
        });
    }

    @Test
    void testRemoveWriterBook() {
        Book book = bookRepository.save(getDefaultBook());

        Writer writer = getDefaultWriter();
        writer.addBook(book);
        writer = writerRepository.save(writer);

        String url = String.format(API_WRITERS_WRITER_ID_BOOKS_BOOK_ID_URL, writer.getId(), book.getId());
        ResponseEntity<WriterResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, WriterResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(writer.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(writer.getName());
        assertThat(responseEntity.getBody().getBooks().size()).isEqualTo(0);

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertThat(writerOptional.isPresent()).isTrue();
        writerOptional.ifPresent(w -> assertThat(w.getBooks().size()).isEqualTo(0));

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertThat(bookOptional.isPresent()).isTrue();
        bookOptional.ifPresent(b -> assertThat(b.getWriters().size()).isEqualTo(0));
    }

    @Test
    void testGetBook() {
        Book book = bookRepository.save(getDefaultBook());

        String url = String.format(API_BOOKS_BOOK_ID_URL, book.getId());
        ResponseEntity<BookResponse> responseEntity = testRestTemplate.getForEntity(url, BookResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(book.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(book.getName());
        assertThat(responseEntity.getBody().getWriters().size()).isEqualTo(0);
    }

    @Test
    void testCreateBook() {
        CreateBookRequest createBookRequest = new CreateBookRequest("Introduction to Java 8");
        ResponseEntity<BookResponse> responseEntity = testRestTemplate.postForEntity(
                API_BOOKS_URL, createBookRequest, BookResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(createBookRequest.getName());
        assertThat(responseEntity.getBody().getWriters().size()).isEqualTo(0);

        Optional<Book> bookOptional = bookRepository.findById(responseEntity.getBody().getId());
        assertThat(bookOptional.isPresent()).isTrue();
        bookOptional.ifPresent(b -> assertThat(b.getName()).isEqualTo(createBookRequest.getName()));
    }

    @Test
    void testUpdateBook() {
        Book book = bookRepository.save(getDefaultBook());
        UpdateBookRequest updateBookRequest = new UpdateBookRequest("Introduction to Java 11");

        HttpEntity<UpdateBookRequest> requestUpdate = new HttpEntity<>(updateBookRequest);
        String url = String.format(API_BOOKS_BOOK_ID_URL, book.getId());
        ResponseEntity<BookResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, requestUpdate, BookResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(updateBookRequest.getName());

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertThat(bookOptional.isPresent()).isTrue();
        bookOptional.ifPresent(b -> assertThat(b.getName()).isEqualTo(updateBookRequest.getName()));
    }

    @Test
    void testDeleteBook() {
        Book book = bookRepository.save(getDefaultBook());

        String url = String.format(API_BOOKS_BOOK_ID_URL, book.getId());
        ResponseEntity<BookResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, BookResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(book.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(book.getName());
        assertThat(responseEntity.getBody().getWriters().size()).isEqualTo(0);

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertThat(bookOptional.isPresent()).isFalse();
    }

    @Test
    void testAddBookWriter() {
        Book book = bookRepository.save(getDefaultBook());
        Writer writer = writerRepository.save(getDefaultWriter());

        String url = String.format(API_BOOKS_BOOK_ID_WRITERS_WRITER_ID_URL, book.getId(), writer.getId());
        ResponseEntity<BookResponse> responseEntity = testRestTemplate.postForEntity(url, null, BookResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(book.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(book.getName());
        assertThat(responseEntity.getBody().getWriters().size()).isEqualTo(1);
        assertThat(responseEntity.getBody().getWriters().get(0).getId()).isEqualTo(writer.getId());

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertThat(bookOptional.isPresent()).isTrue();
        bookOptional.ifPresent(b -> {
            assertThat(b.getWriters().size()).isEqualTo(1);
            assertThat(b.getWriters().contains(writer)).isTrue();
        });

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertThat(writerOptional.isPresent()).isTrue();
        writerOptional.ifPresent(w -> {
            assertThat(w.getBooks().size()).isEqualTo(1);
            assertThat(w.getBooks().contains(book)).isTrue();
        });
    }

    @Test
    void testRemoveBookWriter() {
        Book book = bookRepository.save(getDefaultBook());
        Writer writer = writerRepository.save(getDefaultWriter());

        String url = String.format(API_BOOKS_BOOK_ID_WRITERS_WRITER_ID_URL, book.getId(), writer.getId());
        ResponseEntity<BookResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, BookResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(book.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(book.getName());
        assertThat(responseEntity.getBody().getWriters().size()).isEqualTo(0);

        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertThat(bookOptional.isPresent()).isTrue();
        bookOptional.ifPresent(b -> assertThat(b.getWriters().size()).isEqualTo(0));

        Optional<Writer> writerOptional = writerRepository.findById(writer.getId());
        assertThat(writerOptional.isPresent()).isTrue();
        writerOptional.ifPresent(w -> assertThat(w.getBooks().size()).isEqualTo(0));
    }

    private Writer getDefaultWriter() {
        Writer writer = new Writer();
        writer.setName("Ivan Franchin");
        return writer;
    }

    private Book getDefaultBook() {
        Book book = new Book();
        book.setName("Introduction to Java 8");
        return book;
    }

    private static final String API_WRITERS_URL = "/api/writers";
    private static final String API_WRITERS_WRITER_ID_URL = "/api/writers/%s";
    private static final String API_BOOKS_URL = "/api/books";
    private static final String API_BOOKS_BOOK_ID_URL = "/api/books/%s";
    private static final String API_WRITERS_WRITER_ID_BOOKS_BOOK_ID_URL = "/api/writers/%s/books/%s";
    private static final String API_BOOKS_BOOK_ID_WRITERS_WRITER_ID_URL = "/api/books/%s/writers/%s";
}