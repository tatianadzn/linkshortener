package com.nordcodes.testassignment.linkshortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Link has been already registered")
public class LinkHasBeenAlreadyRegisteredException extends RuntimeException {

    public LinkHasBeenAlreadyRegisteredException(String message) {
        super(message);
    }
}
