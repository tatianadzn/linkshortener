package com.nordcodes.testassignment.linkshortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Database operation failed")
public class DatabaseException extends RuntimeException {

    public DatabaseException(String message) {
        super(message);
    }
}
