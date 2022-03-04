package com.nordcodes.testassignment.linkshortener.service;

import com.nordcodes.testassignment.linkshortener.dao.LinksDao;
import com.nordcodes.testassignment.linkshortener.entity.Link;
import com.nordcodes.testassignment.linkshortener.entity.LinkRegisterRequest;
import com.nordcodes.testassignment.linkshortener.exceptions.DatabaseException;
import com.nordcodes.testassignment.linkshortener.exceptions.ShortLinkGenerationException;
import com.nordcodes.testassignment.linkshortener.utils.ShortLinkGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinksService {
    private static final int MAX_NUM_TRIES = 5;

    private final LinksDao linksDao;

    @Autowired
    public LinksService(LinksDao linksDao) {
        this.linksDao = linksDao;
    }

    public List<Link> loadAll() {
        return linksDao.loadAll();
    }

    public String registerLink(final LinkRegisterRequest linkRegisterRequest) {
        final String shortLink = generateShortLink();
        final int result = linksDao.registerLink(linkRegisterRequest, shortLink);
        if (result != 1) {
            throw new DatabaseException("Link cannot be registered due to database issues: " + linkRegisterRequest.getFullLink());
        }
        return shortLink;
    }

    public String loadFullLink(final String shortLink) {
        return linksDao.loadFullLink(shortLink);
    }

    private String generateShortLink() {
        for (int i = 0; i < MAX_NUM_TRIES; i++) {
            final String shortLink = ShortLinkGenerator.generate();

            if (!linksDao.checkIfExistsShortLink(shortLink)) {
                return shortLink;
            }
        }
        throw new ShortLinkGenerationException("Cannot generate unique short link");
    }
}
