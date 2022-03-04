package com.nordcodes.testassignment.linkshortener.manager;

import com.nordcodes.testassignment.linkshortener.dao.UserDao;
import com.nordcodes.testassignment.linkshortener.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager {

    private final UserDao userDao;

    @Autowired
    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }


    public User loadUserById(long userId) {
        return userDao.loadUserById(userId);
    }
}
