package com.test.mockito.mainproject.dao;

import com.test.mockito.mainproject.model.UserAccount;

public class AccountDao {

    public UserAccount findUserAccount(String username, String password){
//        throw new UnsupportedOperationException();
        return new UserAccount();
    }

}
