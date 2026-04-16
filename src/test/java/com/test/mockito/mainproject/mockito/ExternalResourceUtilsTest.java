package com.test.mockito.mainproject.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;


class ExternalResourceUtilsTest {

    @Test
    void testMockStaticMethod() {
        try (MockedStatic<ExternalResourceUtils> mock = Mockito.mockStatic(ExternalResourceUtils.class)) {
            mock.when(ExternalResourceUtils::foo).thenReturn("Mockito");
            String result = ExternalResourceUtils.foo();
            assertThat(result, equalTo("Mockito"));
        }
    }
}