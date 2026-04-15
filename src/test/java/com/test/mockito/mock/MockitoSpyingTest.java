package com.test.mockito.mock;

import com.test.mockito.mainproject.mockito.PartialService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@DisplayName("Spying tutorial")
@ExtendWith(MockitoExtension.class)
public class MockitoSpyingTest {

    private List<String> arrayList;

    @Spy
    private LinkedList<String> linkedList;

    @BeforeEach
    void setUp() {
        this.arrayList = spy(ArrayList.class);
    }

    @Test
    void testMockitoSpyingOnArrayList() {
        arrayList.add("Hello");
        arrayList.add("Mockito");

        Assertions.assertAll(
                () -> MatcherAssert.assertThat(this.arrayList, hasItems("Hello")),
                () -> MatcherAssert.assertThat(this.arrayList, hasSize(2)),
                () -> MatcherAssert.assertThat(this.arrayList.get(1), is(equalTo("Mockito")))
        );

    }

    @Test
    void testMockitoSpyingOnLinkedList() {
        linkedList.add("Hello");
        linkedList.add("Mockito");

        Assertions.assertAll(
                () -> MatcherAssert.assertThat(this.linkedList, hasItems("Hello")),
                () -> MatcherAssert.assertThat(this.linkedList, hasSize(2)),
                () -> MatcherAssert.assertThat(this.linkedList.get(1), is(equalTo("Mockito")))
        );

    }

    @Spy
    private PartialService partialService;

    @Test
    void testSpyingOnPartialService() {
        Assertions.assertAll(
                () -> MatcherAssert.assertThat(partialService.getRandom(), notNullValue()),
                () -> MatcherAssert.assertThat(partialService.getRandom(), lessThan(100)),
                () -> Assertions.assertThrows(RuntimeException.class, () -> partialService.getFromExternal())
        );
        // Со Spy объектом не работает
//        when(partialService.getFromExternal()).thenReturn(5);
//        Assertions.assertEquals(5, partialService.getFromExternal());

        doReturn(5).when(partialService).getFromExternal();
        MatcherAssert.assertThat(partialService.getFromExternal(), equalTo(5));
    }
}