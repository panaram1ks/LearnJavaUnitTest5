package com.test.mockito.mainproject.service;

import com.test.mockito.mainproject.dao.UserDao;
import com.test.mockito.mainproject.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockitoInjectMockTest {

    @Spy
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

//    @BeforeEach
//    void init() {
//        this.userService = new UserService(userDao);
//    }

    @AfterEach
    void tearDown() {
        reset(userDao);
    }

    @Test
    void createNewUserTest() {
        // stabbing
        Mockito.doReturn(false).when(userDao).exist(isA(User.class));
        Mockito.doReturn(1).when(userDao).saveUser(isA(User.class));

        // data
        final User user = new User();
        final int effortLine = userService.saveOrUpdate(user);

        // check assertions
//        MatcherAssert.assertThat();
        assertAll(
                () -> assertThat(effortLine, equalTo(1)),
                () -> verify(userDao, times(0)).updateUser(any(User.class)),
                () -> verify(userDao, times(1)).saveUser(any(User.class))
        );
    }

}