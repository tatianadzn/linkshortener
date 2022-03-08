package com.nordcodes.testassignment.linkshortener.entity;

public class LinkRegisterRequest {
    private long user_id;
    private String fullLink;
    private int expirationTimeInDays;

    public LinkRegisterRequest(long user_id, String fullLink, int expirationTimeInDays) {
        this.user_id = user_id;
        this.fullLink = fullLink;
        this.expirationTimeInDays = expirationTimeInDays;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getFullLink() {
        return fullLink;
    }

    public void setFullLink(String fullLink) {
        this.fullLink = fullLink;
    }

    public int getExpirationTimeInDays() {
        return expirationTimeInDays;
    }

    public void setExpirationTimeInDays(int expirationTimeInDays) {
        this.expirationTimeInDays = expirationTimeInDays;
    }
}
