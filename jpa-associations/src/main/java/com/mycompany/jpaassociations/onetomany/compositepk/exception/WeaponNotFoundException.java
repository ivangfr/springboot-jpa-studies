package com.mycompany.jpaassociations.onetomany.compositepk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WeaponNotFoundException extends RuntimeException {

    public WeaponNotFoundException(String message) {
        super(message);
    }
}
