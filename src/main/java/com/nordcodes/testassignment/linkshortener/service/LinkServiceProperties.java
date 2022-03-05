package com.nordcodes.testassignment.linkshortener.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service.links")
public class LinkServiceProperties {
    private int statsCount;

    public int getStatsCount() {
        return statsCount;
    }

    public void setStatsCount(int statsCount) {
        this.statsCount = statsCount;
    }
}
