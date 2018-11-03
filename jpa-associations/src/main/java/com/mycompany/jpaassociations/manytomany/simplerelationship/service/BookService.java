package com.mycompany.jpaassociations.manytomany.simplerelationship.service;

import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Book;

public interface BookService {

    Book validateAndGetBook(Long id);

    Book saveBook(Book book);

    void deleteBook(Book book);

}
