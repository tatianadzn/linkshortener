package com.nordcodes.testassignment.linkshortener.dao;

import com.nordcodes.testassignment.linkshortener.entity.Link;
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

import java.sql.Timestamp;
import java.util.List;

@Component
public class LinksDao {

    private static final String INSERT_QUERY = "INSERT INTO link (short_link, full_link, expiration_datetime) VALUES (?, ?, ?)";
    private static final String IF_EXISTS_SHORT_LINK_QUERY = "SELECT EXISTS(SELECT 1 FROM link WHERE short_link = ?)";
    private static final String LOAD_LINK_BY_SHORT_LINK_QUERY = "SELECT * FROM link WHERE short_link = ?";
    private static final String DELETE_LINK_QUERY = "DELETE FROM link WHERE short_link = ?";

    private static final String LOAD_STATS_ALL_QUERY = "SELECT link.short_link, COUNT(log.user_id) AS click_count "
            + "FROM clicking_log as log "
            + "RIGHT JOIN link ON log.short_link = link.short_link "
            + "GROUP BY link.short_link "
            + "ORDER BY click_count DESC "
            + "LIMIT ?";

    private static final String LOAD_STATS_UNIQUE_QUERY = "SELECT link.short_link, COUNT(DISTINCT log.user_id) AS click_count "
            + "FROM clicking_log as log "
            + "RIGHT JOIN link ON log.short_link = link.short_link "
            + "GROUP BY link.short_link "
            + "ORDER BY click_count DESC "
            + "LIMIT ?";

    private static final String LOG_CLICKING_QUERY = "INSERT INTO clicking_log (short_link, user_id) VALUES (?, ?)";

    private static final String FULL_LINK_ROW = "full_link";
    private static final String SHORT_LINK_ROW = "short_link";
    private static final String CLICK_COUNT_ROW = "click_count";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LinksDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void registerLink(final String fullLink, final String shortLink, final Timestamp expiration) {
        if (jdbcTemplate.update(INSERT_QUERY, shortLink, fullLink, expiration) != 1) {
            throw new DatabaseException("Link cannot be registered");
        }
    }

    public Link loadFullLink(final String shortLink) {
        try {
            return jdbcTemplate.queryForObject(LOAD_LINK_BY_SHORT_LINK_QUERY, getLinkRowMapper(), shortLink);
        } catch (EmptyResultDataAccessException e) {
            throw new LinkNotFoundException("Link not found");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void logClicking(final String shortLink, final long userId) {
        if (jdbcTemplate.update(LOG_CLICKING_QUERY, shortLink, userId) != 1) {
            throw new DatabaseException("Link click cannot be logged to db");
        }
    }

    public List<Stats> loadStatsAll(final int count) {
        return jdbcTemplate.query(LOAD_STATS_ALL_QUERY, getStatsRowMapper(), count);
    }

    public List<Stats> loadStatsUnique(final int count) {
        return jdbcTemplate.query(LOAD_STATS_UNIQUE_QUERY, getStatsRowMapper(), count);
    }

    public int deleteLink(final String shortLink) {
        return jdbcTemplate.update(DELETE_LINK_QUERY, shortLink);
    }

    public Boolean checkIfExistsShortLink(final String shortLink) {
        return jdbcTemplate.queryForObject(IF_EXISTS_SHORT_LINK_QUERY, Boolean.class, shortLink);
    }

    private RowMapper<Link> getLinkRowMapper() {
        return (rs, num) -> {
            Link link = new Link();
            link.setFullLink(rs.getString(FULL_LINK_ROW));
            link.setShortLink(rs.getString(SHORT_LINK_ROW));
            link.setExpirationDateTime(rs.getTimestamp("expiration_datetime"));
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
