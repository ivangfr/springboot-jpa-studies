package com.mycompany.jpalocking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RedeemRaceConditionException extends RuntimeException {

    public RedeemRaceConditionException(String message) {
        super(message);
    }
}
