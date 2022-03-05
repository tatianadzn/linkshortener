package com.nordcodes.testassignment.linkshortener.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service.links")
public class LinkServiceProperties {
    private int statsCountAll;
    private int statsCountUnique;

    public int getStatsCountAll() {
        return statsCountAll;
    }

    public void setStatsCountAll(int statsCountAll) {
        this.statsCountAll = statsCountAll;
    }

    public int getStatsCountUnique() {
        return statsCountUnique;
    }

    public void setStatsCountUnique(int statsCountUnique) {
        this.statsCountUnique = statsCountUnique;
    }
}
