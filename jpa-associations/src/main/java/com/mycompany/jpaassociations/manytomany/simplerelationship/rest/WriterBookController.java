package com.mycompany.jpaassociations.manytomany.simplerelationship.rest;

import com.mycompany.jpaassociations.manytomany.simplerelationship.mapper.BookMapper;
import com.mycompany.jpaassociations.manytomany.simplerelationship.mapper.WriterMapper;
import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Book;
import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Writer;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.BookDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.CreateBookDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.CreateWriterDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateBookDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.UpdateWriterDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto.WriterDto;
import com.mycompany.jpaassociations.manytomany.simplerelationship.service.BookService;
import com.mycompany.jpaassociations.manytomany.simplerelationship.service.WriterService;
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
@RequestMapping("/api")
public class WriterBookController {

    private final WriterService writerService;
    private final BookService bookService;
    private final WriterMapper writerMapper;
    private final BookMapper bookMapper;

    // ------
    // Writer

    @GetMapping("/writers/{writerId}")
    public WriterDto getWriter(@PathVariable Long writerId) {
        Writer writer = writerService.validateAndGetWriter(writerId);
        return writerMapper.toWriterDto(writer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/writers")
    public WriterDto createWriter(@Valid @RequestBody CreateWriterDto createWriterDto) {
        Writer writer = writerMapper.toWriter(createWriterDto);
        writer = writerService.saveWriter(writer);
        return writerMapper.toWriterDto(writer);
    }

    @PutMapping("/writers/{writerId}")
    public WriterDto updateWriter(@PathVariable Long writerId, @Valid @RequestBody UpdateWriterDto updateWriterDto) {
        Writer writer = writerService.validateAndGetWriter(writerId);
        writerMapper.updateWriterFromDto(updateWriterDto, writer);
        writer = writerService.saveWriter(writer);
        return writerMapper.toWriterDto(writer);
    }

    @DeleteMapping("/writers/{writerId}")
    public WriterDto deleteWriter(@PathVariable Long writerId) {
        Writer writer = writerService.validateAndGetWriter(writerId);
        writer.getBooks().forEach(book -> book.removeWriter(writer));
        writerService.deleteWriter(writer);
        return writerMapper.toWriterDto(writer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/writers/{writerId}/books/{bookId}")
    public WriterDto addWriterBook(@PathVariable Long writerId, @PathVariable Long bookId) {
        Writer writer = writerService.validateAndGetWriter(writerId);
        Book book = bookService.validateAndGetBook(bookId);
        writer.addBook(book);
        writer = writerService.saveWriter(writer);
        return writerMapper.toWriterDto(writer);
    }

    @DeleteMapping("/writers/{writerId}/books/{bookId}")
    public WriterDto removeWriterBook(@PathVariable Long writerId, @PathVariable Long bookId) {
        Writer writer = writerService.validateAndGetWriter(writerId);
        Book book = bookService.validateAndGetBook(bookId);
        writer.removeBook(book);
        writer = writerService.saveWriter(writer);
        return writerMapper.toWriterDto(writer);
    }

    // -----
    // Books

    @GetMapping("/books/{bookId}")
    public BookDto getBook(@PathVariable Long bookId) {
        Book book = bookService.validateAndGetBook(bookId);
        return bookMapper.toBookDto(book);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books")
    public BookDto createBook(@Valid @RequestBody CreateBookDto createBookDto) {
        Book book = bookMapper.toBook(createBookDto);
        book = bookService.saveBook(book);
        return bookMapper.toBookDto(book);
    }

    @PutMapping("/books/{bookId}")
    public BookDto updateBook(@PathVariable Long bookId, @Valid @RequestBody UpdateBookDto updateBookDto) {
        Book book = bookService.validateAndGetBook(bookId);
        bookMapper.updateBookFromDto(updateBookDto, book);
        book = bookService.saveBook(book);
        return bookMapper.toBookDto(book);
    }

    @DeleteMapping("/books/{bookId}")
    public BookDto deleteBook(@PathVariable Long bookId) {
        Book book = bookService.validateAndGetBook(bookId);
        book.getWriters().forEach(writer -> writer.removeBook(book));
        bookService.deleteBook(book);
        return bookMapper.toBookDto(book);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books/{bookId}/writers/{writerId}")
    public BookDto addBookWriter(@PathVariable Long bookId, @PathVariable Long writerId) {
        Book book = bookService.validateAndGetBook(bookId);
        Writer writer = writerService.validateAndGetWriter(writerId);
        book.addWriter(writer);
        book = bookService.saveBook(book);
        return bookMapper.toBookDto(book);
    }

    @DeleteMapping("/books/{bookId}/writers/{writerId}")
    public BookDto removeBookWriter(@PathVariable Long bookId, @PathVariable Long writerId) {
        Book book = bookService.validateAndGetBook(bookId);
        Writer writer = writerService.validateAndGetWriter(writerId);
        book.removeWriter(writer);
        book = bookService.saveBook(book);
        return bookMapper.toBookDto(book);
    }

}
