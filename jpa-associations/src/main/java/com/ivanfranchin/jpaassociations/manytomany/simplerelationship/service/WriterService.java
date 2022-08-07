package com.ivanfranchin.jpaassociations.manytomany.simplerelationship.service;

import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.model.Writer;

public interface WriterService {

    Writer validateAndGetWriter(Long id);

    Writer saveWriter(Writer writer);

    void deleteWriter(Writer writer);
}
