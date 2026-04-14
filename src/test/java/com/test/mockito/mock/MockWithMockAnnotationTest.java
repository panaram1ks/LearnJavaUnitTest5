package com.test.mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MockWithMockAnnotationTest {

    @Mock
    private List<String> mockList;

    @Test
    void createMockWithAnnotation() {
        mockList.add("mockito");
        assertThat(mockList.size(), is(equalTo(0)));
    }


}