package com.test.mockito.mainproject.service;

import com.test.mockito.mainproject.dao.UserDao;
import com.test.mockito.mainproject.model.User;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public int saveOrUpdate(User user) {
        return this.userDao.merge(user);
    }
}
