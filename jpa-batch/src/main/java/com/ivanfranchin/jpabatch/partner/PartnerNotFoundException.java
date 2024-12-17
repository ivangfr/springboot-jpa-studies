package com.ivanfranchin.jpabatch.partner;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PartnerNotFoundException extends RuntimeException {

    public PartnerNotFoundException(Long id) {
        super(String.format("Partner with id '%s' not found", id));
    }
}
