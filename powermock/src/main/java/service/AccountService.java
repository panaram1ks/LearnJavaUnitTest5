package service;

import dao.AccountDao;
import lombok.RequiredArgsConstructor;
import model.UserAccount;

@RequiredArgsConstructor
public class AccountService {

    private final AccountDao accountDao;

    public UserAccount auth(String username, String password){
       return accountDao.findUserAccount(username, password);
    }
}
