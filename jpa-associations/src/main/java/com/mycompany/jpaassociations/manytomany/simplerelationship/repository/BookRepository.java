package com.mycompany.jpaassociations.manytomany.simplerelationship.repository;

import com.mycompany.jpaassociations.manytomany.simplerelationship.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
