package controller;

import dao.AccountDao;
import jakarta.servlet.http.HttpServletRequest;
import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import service.AccountService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
public class LoginControllerTest {

    private static final String username = "admin";
    private static final String password = "123456";

    private HttpServletRequest request;
    private AccountDao accountDao;
    private LoginController loginController;

    @Before
    public void setUp() {
        this.accountDao = PowerMockito.mock(AccountDao.class);
        this.request = PowerMockito.mock(HttpServletRequest.class);
        AccountService accountService = new AccountService(accountDao);
        this.loginController = new LoginController(accountService);
    }

    @Test
    public void testLoginAuthSuccess() {
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);

        when(accountDao.findUserAccount(username, password)).thenReturn(new UserAccount());
        final var result = loginController.login(request);
        assertThat(result, equalTo("main"));
    }

    @Test
    public void testLoginAuthFailure() {
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(accountDao.findUserAccount(username, password)).thenReturn(null);

        String result = loginController.login(request);
        assertThat(result, equalTo("login"));
    }


    @Test
    public void testLoginAuthErrorDueToDatabaseCrashed() {
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(accountDao.findUserAccount(anyString(), anyString())).thenThrow(RuntimeException.class);

        String result = loginController.login(request);
        assertThat(result, is(equalTo("5xx")));
    }

}