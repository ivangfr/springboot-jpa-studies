package com.ivanfranchin.jpalocking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InsufficientStarsException extends RuntimeException {

    public InsufficientStarsException(Long playerId) {
        super(String.format("Player %s has insufficient stars to redeem", playerId));
    }
}
