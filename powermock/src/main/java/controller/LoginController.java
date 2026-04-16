package controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import service.AccountService;

@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;

    public String login(HttpServletRequest request) {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        try {
            final var userAccount = accountService.auth(username, password);
            if (userAccount == null) {
                return "login";
            } else {
                return "main";
            }
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return "5xx";
        }
    }
}
