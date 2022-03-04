package com.nordcodes.testassignment.linkshortener.dao;

import com.nordcodes.testassignment.linkshortener.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    private static final String USERNAME_ROW = "username";
    private static final String SECRET_KEY_ROW = "secret_key";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public User loadUserById(long userId) {
        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE user_id = ?", getRowMapper(), userId);
    }

    private RowMapper<User> getRowMapper() {
        return (rs, num) -> {
            User user = new User();
            user.setUsername(rs.getString(USERNAME_ROW));
            user.setSecretKey(rs.getString(SECRET_KEY_ROW));
            return user;
        };
    }
}
