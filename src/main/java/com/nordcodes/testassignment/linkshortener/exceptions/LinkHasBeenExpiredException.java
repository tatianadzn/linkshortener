package com.nordcodes.testassignment.linkshortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Link has been expired")
public class LinkHasBeenExpiredException extends RuntimeException {

    public LinkHasBeenExpiredException(String message) {
        super(message);
    }
}
