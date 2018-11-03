package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
