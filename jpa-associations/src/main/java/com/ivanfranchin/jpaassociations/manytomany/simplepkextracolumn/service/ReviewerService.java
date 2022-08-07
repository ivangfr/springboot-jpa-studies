package com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.service;

import com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.model.Reviewer;

public interface ReviewerService {

    Reviewer validateAndGetReviewer(Long id);

    Reviewer saveReviewer(Reviewer reviewer);

    void deleteReviewer(Reviewer reviewer);
}
