package com.test.mockito.mainproject.controller;

import com.test.mockito.mainproject.dao.AccountDao;
import com.test.mockito.mainproject.model.UserAccount;
import com.test.mockito.mainproject.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // allow use @Mock
class LoginControllerTest {

    private static final String username = "admin";
private static final String password = "123456";

    @Mock
    private HttpServletRequest request;
    @Mock
    private AccountDao accountDao;

    private LoginController loginController;

    @BeforeEach
    void setUp(){
        AccountService accountService = new AccountService(accountDao);
        this.loginController = new LoginController(accountService);
    }

    @DisplayName("user account is validate")
    @Test
    void testLoginAuthSuccess(){
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);

        when(accountDao.findUserAccount(username, password)).thenReturn(new UserAccount());
        final var result = loginController.login(request);
        assertThat(result, is(equalTo("main")));
    }

    @DisplayName("user account is invalid")
    @Test
    void testLoginAuthFailure(){
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(accountDao.findUserAccount(username, password)).thenReturn(null);

        String result = loginController.login(request);
        assertThat(result, is(equalTo("login")));
    }

    @DisplayName("database is crashed")
    @Test
    void testLoginAuthErrorDueToDatabaseCrashed(){
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(accountDao.findUserAccount(anyString(), anyString())).thenThrow(RuntimeException.class);

        String result = loginController.login(request);
        assertThat(result, is(equalTo("5xx")));
    }


}