package com.nordcodes.testassignment.linkshortener.entity;

public class LinkRegisterRequest {
    private long user_id;
    private String fullLink;
    private int expirationTimeInMins;

    public LinkRegisterRequest(long user_id, String fullLink, String shortLink, int expirationTimeInMins) {
        this.user_id = user_id;
        this.fullLink = fullLink;
        this.expirationTimeInMins = expirationTimeInMins;
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

    public int getExpirationTimeInMins() {
        return expirationTimeInMins;
    }

    public void setExpirationTimeInMins(int expirationTimeInMins) {
        this.expirationTimeInMins = expirationTimeInMins;
    }
}
