package com.mycompany.jpaassociations.manytomany.simplerelationship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WriterNotFoundException extends RuntimeException {

    public WriterNotFoundException(Long id) {
        super(String.format("Writer with id '%s' not found", id));
    }
}
