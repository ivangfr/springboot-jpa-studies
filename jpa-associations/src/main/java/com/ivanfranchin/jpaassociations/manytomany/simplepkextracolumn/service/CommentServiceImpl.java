package com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.service;

import com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.exception.CommentNotFoundException;
import com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.model.Comment;
import com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment validateAndGetComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
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