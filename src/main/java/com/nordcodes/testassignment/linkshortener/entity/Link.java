package com.nordcodes.testassignment.linkshortener.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Link {
    private String fullLink;
    private String shortLink;
    private LocalDateTime expirationDateTime;

    public String getFullLink() {
        return fullLink;
    }

    public void setFullLink(String fullLink) {
        this.fullLink = fullLink;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Timestamp expirationDateTime) {
        this.expirationDateTime = expirationDateTime.toLocalDateTime();
    }
}
