package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.service;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Comment;

public interface CommentService {

    Comment validateAndGetComment(Long id);

    Comment saveComment(Comment comment);

    void deleteComment(Comment comment);
}
