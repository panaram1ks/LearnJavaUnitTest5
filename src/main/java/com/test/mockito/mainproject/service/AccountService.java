package com.test.mockito.mainproject.service;

import com.test.mockito.mainproject.dao.AccountDao;
import com.test.mockito.mainproject.model.UserAccount;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountService {

    private final AccountDao accountDao;

    public UserAccount auth(String username, String password){
       return accountDao.findUserAccount(username, password);
    }
}
