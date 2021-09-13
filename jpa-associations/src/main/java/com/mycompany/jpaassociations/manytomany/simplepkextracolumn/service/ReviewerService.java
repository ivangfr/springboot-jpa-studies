package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.service;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Reviewer;

public interface ReviewerService {

    Reviewer validateAndGetReviewer(Long id);

    Reviewer saveReviewer(Reviewer reviewer);

    void deleteReviewer(Reviewer reviewer);
}
