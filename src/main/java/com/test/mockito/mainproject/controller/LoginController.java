package com.test.mockito.mainproject.controller;

import com.test.mockito.mainproject.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;

    public String login(HttpServletRequest request) {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        final var userAccount = accountService.auth(username, password);
        if (userAccount == null) {
            return "login";
        } else {
            return "main";
        }
    }
}
