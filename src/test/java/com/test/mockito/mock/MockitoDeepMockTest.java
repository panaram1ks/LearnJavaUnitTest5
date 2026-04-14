package com.test.mockito.mock;

import com.test.mockito.mainproject.mockito.ExternalServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MockitoDeepMockTest {

    @Nested
    class NestedNullPointerException {
        @Mock
        private ExternalServiceFactory externalServiceFactory;

        @Test
        void testGetValue() {
            Executable executable = () -> externalServiceFactory.createExternalService().getValue();
            Assertions.assertThrows(NullPointerException.class, executable);
        }
    }

}