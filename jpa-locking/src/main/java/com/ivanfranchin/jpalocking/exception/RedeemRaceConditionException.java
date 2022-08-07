package com.ivanfranchin.jpalocking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RedeemRaceConditionException extends RuntimeException {

    public RedeemRaceConditionException(Long id, Throwable cause) {
        super(String.format("Two or more threads of player %s tried to redeem stars at the same time", id), cause);
    }
}
