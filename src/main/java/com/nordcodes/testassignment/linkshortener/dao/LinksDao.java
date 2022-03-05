package com.nordcodes.testassignment.linkshortener.dao;

import com.nordcodes.testassignment.linkshortener.entity.Link;
import com.nordcodes.testassignment.linkshortener.entity.LinkRegisterRequest;
import com.nordcodes.testassignment.linkshortener.entity.Stats;
import com.nordcodes.testassignment.linkshortener.exceptions.DatabaseException;
import com.nordcodes.testassignment.linkshortener.exceptions.LinkNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class LinksDao {

    private static final String INSERT_QUERY = "INSERT INTO link (short_link, full_link) VALUES (?, ?)";
    private static final String INIT_STATS_QUERY = "INSERT INTO link_stats (short_link, click_count) VALUES (?, ?)";
    private static final String IF_EXISTS_SHORT_LINK_QUERY = "SELECT EXISTS(SELECT 1 FROM link WHERE short_link = ?)";
    private static final String LOAD_FULL_LINK_BY_SHORT_LINK_QUERY = "SELECT full_link FROM link WHERE short_link = ?";
    private static final String DELETE_LINK_QUERY = "DELETE FROM link WHERE short_link = ?";
    private static final String UPDATE_CLICKING_COUNT_QUERY
            = "UPDATE link_stats SET click_count = click_count + 1 WHERE short_link = ?";
    private static final String LOAD_STATS_ALL_QUERY = "SELECT short_link, click_count FROM link_stats ORDER BY click_count desc limit ?";

    private static final String FULL_LINK_ROW = "full_link";
    private static final String SHORT_LINK_ROW = "short_link";
    private static final String CLICK_COUNT_ROW = "click_count";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LinksDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void registerLink(final LinkRegisterRequest linkRegisterRequest, final String shortLink) {
        if (jdbcTemplate.update(INSERT_QUERY, shortLink, linkRegisterRequest.getFullLink()) != 1
                || jdbcTemplate.update(INIT_STATS_QUERY, shortLink, 0) != 1) {
            throw new DatabaseException("Link cannot be registered");
        }
    }

    public List<Link> loadAll() {
        return jdbcTemplate.query("select * from link", getRowMapper());
    }

    public String loadFullLink(final String shortLink) {
        try {
            return jdbcTemplate.queryForObject(LOAD_FULL_LINK_BY_SHORT_LINK_QUERY, String.class, shortLink);
        } catch (EmptyResultDataAccessException e) {
            throw new LinkNotFoundException("Link not found");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateClickingCount(final String shortLink) {
        if (jdbcTemplate.update(UPDATE_CLICKING_COUNT_QUERY, shortLink) != 1) {
            throw new DatabaseException("Link stats cannot be updated");
        }
    }

    public List<Stats> loadStatsAll(final int count) {
        return jdbcTemplate.query(LOAD_STATS_ALL_QUERY, getStatsRowMapper(), count);
    }

    public int deleteLink(final String shortLink) {
        return jdbcTemplate.update(DELETE_LINK_QUERY, shortLink);
    }

    public Boolean checkIfExistsShortLink(final String shortLink) {
        return jdbcTemplate.queryForObject(IF_EXISTS_SHORT_LINK_QUERY, Boolean.class, shortLink);
    }

    private RowMapper<Link> getRowMapper() {
        return (rs, num) -> {
            Link link = new Link();
            link.setFullLink(rs.getString(FULL_LINK_ROW));
            link.setShortLink(rs.getString(SHORT_LINK_ROW));
            return link;
        };
    }

    private RowMapper<Stats> getStatsRowMapper() {
        return (rs, num) -> {
            Stats stats = new Stats();
            stats.setShort_link(rs.getString(SHORT_LINK_ROW));
            stats.setCount(rs.getInt(CLICK_COUNT_ROW));
            return stats;
        };
    }
}
