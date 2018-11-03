package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.service;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.exception.CommentNotFoundException;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Comment;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment valideteAndGetComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(String.format("Comment with id '%s' not found", id)));
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
