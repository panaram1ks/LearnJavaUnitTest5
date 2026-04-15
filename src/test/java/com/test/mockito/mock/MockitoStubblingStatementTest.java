package com.test.mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Mockito stabling statement")
class MockitoStablingStatementTest {

    @DisplayName("when...thenReturn / doReturn...when")
    @Nested
    class NestedClassWhenThenReturnAndDoReturnWhen {

        @Mock
        private List<String> list;

        @Test
        void testWhenThenReturnStatement() {
            String exampleStr = "first element";
            when(list.get(0)).thenReturn(exampleStr);

            assertThat(list.get(0), is(equalTo(exampleStr)));
            assertThat(list.get(1), nullValue());
        }

        @Test
        void testDoReturnWhenStatement(){
            String exampleStr = "first element";
            doReturn(exampleStr).when(list).get(0);
            doReturn(null).when(list).get(1);

            assertThat(list.get(0), is(equalTo(exampleStr)));
            assertThat(list.get(1), nullValue());
        }
    }

}