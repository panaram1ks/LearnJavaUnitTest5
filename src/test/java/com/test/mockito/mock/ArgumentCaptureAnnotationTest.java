package com.test.mockito.mock;

import com.test.mockito.mainproject.dao.UserDao;
import com.test.mockito.mainproject.model.User;
import com.test.mockito.mainproject.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Mockito capture argument annotation demo")
@ExtendWith(MockitoExtension.class)
class ArgumentCaptureAnnotationTest {

    @Spy
    private UserDao userDao;

    private UserService userService;

    @Captor
    private ArgumentCaptor<User> argumentCaptor;

    @BeforeEach
    void init() {
        this.userService = new UserService(userDao);
    }

    @AfterEach
    void destroy() {
        Mockito.reset(userDao);
    }

    @Test
    void testMockitoArgumentCapture() {
        Mockito.doReturn(1).when(userDao).updateUser(ArgumentMatchers.isA(User.class));
//        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        int result = userService.deleteUser("Bob");

        assertAll(
                () -> verify(userDao, times(1)).updateUser(argumentCaptor.capture()),
                () -> assertThat(argumentCaptor.getValue().getId(), equalTo("Bob")),
                () -> assertThat(argumentCaptor.getValue().getStatus(), equalTo("D")),
                () -> assertThat(result, equalTo(1))
        );

    }

}