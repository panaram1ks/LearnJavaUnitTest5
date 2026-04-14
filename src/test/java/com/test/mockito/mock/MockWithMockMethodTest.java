package com.test.mockito.mock;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

class MockWithMockMethodTest {

    @Test
    void createMockObjectWithMockMethod() {
        final List mockList = mock(List.class);
        mockList.add("mockito");
        assertThat(mockList.size(), is(equalTo(0)));
    }

}