package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.service;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.exception.ReviewerNotFoundException;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Reviewer;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository.ReviewerRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewerServiceImpl implements ReviewerService {

    private final ReviewerRepository reviewerRepository;

    public ReviewerServiceImpl(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    @Override
    public Reviewer validateAndGetReviewer(Long id) {
        return reviewerRepository.findById(id).orElseThrow(() -> new ReviewerNotFoundException(String.format("Reviewer with id '%s' not found", id)));
    }

    @Override
    public Reviewer saveReviewer(Reviewer reviewer) {
        return reviewerRepository.save(reviewer);
    }

    @Override
    public void deleteReviewer(Reviewer reviewer) {
        reviewerRepository.delete(reviewer);
    }
}