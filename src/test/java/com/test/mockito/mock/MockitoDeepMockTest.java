package com.test.mockito.mock;

import com.test.mockito.mainproject.mockito.ExternalService;
import com.test.mockito.mainproject.mockito.ExternalServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MockitoDeepMockTest {

    @Nested
    class NestedNullPointerException {

        @Mock(answer = Answers.RETURNS_DEEP_STUBS)
        private ExternalServiceFactory externalServiceFactory;

        @Test
        void testGetValue() {
            String value = externalServiceFactory.createExternalService().getValue();
            assertThat(value, is(equalTo(null)));
        }
    }

    @Nested
    class NestedStubbedStatement {
        @Mock
        private ExternalServiceFactory externalServiceFactory;
        @Mock
        private ExternalService externalService;

        @Test
        void testGetValue() {
            doReturn(externalService).when(externalServiceFactory).createExternalService();
            doReturn("123").when(externalService).getValue();
            String value = externalServiceFactory.createExternalService().getValue();
            assertThat(value, is(equalTo("123")));
        }
    }

}