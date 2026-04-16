package com.test.mockito.mainproject.mockito;

import static org.junit.jupiter.api.Assertions.*;

import com.test.mockito.mainproject.dao.UserDao;
import com.test.mockito.mainproject.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
class FinalExternalResourceTest {

    @Test
    void testFinalClassMock(@Mock FinalExternalResource finalExternalResource){
        when(finalExternalResource.foo()).thenReturn(10);
        int foo = finalExternalResource.foo();
        assertThat(foo, equalTo(10));
    }
}