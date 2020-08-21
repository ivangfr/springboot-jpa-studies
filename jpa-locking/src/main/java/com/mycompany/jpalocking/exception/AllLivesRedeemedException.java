package com.mycompany.jpalocking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AllLivesRedeemedException extends RuntimeException {

    public AllLivesRedeemedException() {
        super("There are no lives to be redeemed.");
    }
}
