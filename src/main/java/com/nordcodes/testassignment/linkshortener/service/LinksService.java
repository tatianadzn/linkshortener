package com.nordcodes.testassignment.linkshortener.service;

import com.nordcodes.testassignment.linkshortener.dao.LinksDao;
import com.nordcodes.testassignment.linkshortener.entity.Link;
import com.nordcodes.testassignment.linkshortener.entity.LinkRegisterRequest;
import com.nordcodes.testassignment.linkshortener.entity.Stats;
import com.nordcodes.testassignment.linkshortener.exceptions.DatabaseException;
import com.nordcodes.testassignment.linkshortener.exceptions.LinkNotFoundException;
import com.nordcodes.testassignment.linkshortener.exceptions.ShortLinkGenerationException;
import com.nordcodes.testassignment.linkshortener.utils.ShortLinkGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LinksService {
    private static final int MAX_NUM_TRIES = 5;

    private final LinksDao linksDao;
    private final LinkServiceProperties properties;

    @Autowired
    public LinksService(LinksDao linksDao, LinkServiceProperties properties) {
        this.linksDao = linksDao;
        this.properties = properties;
    }

    public List<Link> loadAll() {
        return linksDao.loadAll();
    }

    @Transactional
    public String loadFullLink(final String shortLink, final long userId) {
        linksDao.logClicking(shortLink, userId);
        return linksDao.loadFullLink(shortLink);
    }

    @Transactional
    public String registerLink(final LinkRegisterRequest linkRegisterRequest) {
        final String shortLink = generateUniqueShortLink();
        linksDao.registerLink(linkRegisterRequest, shortLink);

        return shortLink;
    }

    @Transactional
    public void deleteLink(final String shortLink) {
        if (!linksDao.checkIfExistsShortLink(shortLink)) {
            throw new LinkNotFoundException("No such link was registered: " + shortLink);
        }

        final int result = linksDao.deleteLink(shortLink);
        if (result != 1) {
            throw new DatabaseException("Link cannot be deleted due to database issues");
        }
    }

    public List<Stats> loadStatsAll(final Integer count) {
        int statsCount = Optional.ofNullable(count).orElse(properties.getStatsCountAll());
        return linksDao.loadStatsAll(statsCount);
    }

    public List<Stats> loadStatsUnique(final Integer count) {
        int statsCount = Optional.ofNullable(count).orElse(properties.getStatsCountUnique());
        return linksDao.loadStatsUnique(statsCount);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private String generateUniqueShortLink() {
        for (int i = 0; i < MAX_NUM_TRIES; i++) {
            final String shortLink = ShortLinkGenerator.generate();

            if (!linksDao.checkIfExistsShortLink(shortLink)) {
                return shortLink;
            }
        }
        throw new ShortLinkGenerationException("Cannot generate unique short link");
    }
}
