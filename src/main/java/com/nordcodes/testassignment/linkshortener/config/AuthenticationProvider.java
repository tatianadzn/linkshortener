package com.nordcodes.testassignment.linkshortener.config;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthenticationProvider {

    public void validateToken(final String token, Map<String, String[]> parameters) {
        // form token manually from request
        // get params
        // get secret from db
        // combine and apply SHA-1
        // is equals
    }
}
