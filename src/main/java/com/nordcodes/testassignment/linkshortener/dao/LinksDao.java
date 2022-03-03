package com.nordcodes.testassignment.linkshortener.dao;

import com.nordcodes.testassignment.linkshortener.entity.Link;
import com.nordcodes.testassignment.linkshortener.entity.LinkRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LinksDao {

    private static final String INSERT_QUERY = "INSERT INTO link (short_link, full_link, click_count) VALUES (?, ?, ?)";
    private static final String IF_EXISTS_SHORT_LINK_QUERY = "SELECT EXISTS(SELECT 1 FROM link WHERE short_link = ?)";
    private static final String IF_EXISTS_FULL_LINK_QUERY = "SELECT EXISTS(SELECT 1 FROM link WHERE full_link = ?)";

    private static final String FULL_LINK_ROW = "full_link";
    private static final String SHORT_LINK_ROW = "short_link";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LinksDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int registerLink(final LinkRegisterRequest linkRegisterRequest, final String shortLink) {
        return jdbcTemplate.update(INSERT_QUERY, shortLink, linkRegisterRequest.getFullLink(), 0);
    }

    public List<Link> loadAll() {
        return jdbcTemplate.query("select * from link", getRowMapper());
    }

    public Boolean checkIfExistsShortLink(final String shortLink) {
        return jdbcTemplate.queryForObject(IF_EXISTS_SHORT_LINK_QUERY, Boolean.class, shortLink);
    }

    public Boolean checkIfExistsFullLink(final String fullLink) {
        return jdbcTemplate.queryForObject(IF_EXISTS_FULL_LINK_QUERY, Boolean.class, fullLink);
    }

    private RowMapper<Link> getRowMapper() {
        return (rs, num) -> {
            Link link = new Link();
            link.setFullLink(rs.getString(FULL_LINK_ROW));
            link.setShortLink(rs.getString(SHORT_LINK_ROW));
            return link;
        };
    }
}
