package com.nordcodes.testassignment.linkshortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Unique short link cannot be generated")
public class ShortLinkGenerationException extends RuntimeException {

    public ShortLinkGenerationException(String message) {
        super(message);
    }
}
