package com.ivanfranchin.jpaassociations.manytomany.simplerelationship.service;

import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.exception.BookNotFoundException;
import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.model.Book;
import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book validateAndGetBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }
}
