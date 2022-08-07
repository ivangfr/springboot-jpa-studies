package com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.service;

import com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.model.Comment;

public interface CommentService {

    Comment validateAndGetComment(Long id);

    Comment saveComment(Comment comment);

    void deleteComment(Comment comment);
}
