package com.ivanfranchin.jpaassociations.manytomany.simplerelationship.repository;

import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.model.Writer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterRepository extends CrudRepository<Writer, Long> {
}
