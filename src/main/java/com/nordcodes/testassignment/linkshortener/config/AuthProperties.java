package com.nordcodes.testassignment.linkshortener.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.auth")
public class AuthProperties {

    private String authHeaderPrefix;

    public String getAuthHeaderPrefix() {
        return authHeaderPrefix;
    }

    public void setAuthHeaderPrefix(String authHeaderPrefix) {
        this.authHeaderPrefix = authHeaderPrefix;
    }
}
