package com.test.mockito.mock;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static com.test.mockito.mainproject.mockito.ClosedRangeArgumentMatcher.closedRange;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DisplayName("The argument Matcher")
@ExtendWith(MockitoExtension.class)
class ArgumentMatcherTest {

    @Test
    void simpleArgumentTest(@Mock List<String> list) {
//        when(list.get(anyInt())).thenReturn("Mockito");
//        when(list.get(isA(Integer.class))).thenReturn("Mockito");
        when(list.get(AdditionalMatchers.or(
                ArgumentMatchers.eq(1), ArgumentMatchers.eq(2)
        ))).thenReturn("Mockito");
        when(list.get(AdditionalMatchers.and(
                AdditionalMatchers.geq(3), AdditionalMatchers.lt(10)
        ))).thenReturn("Power_mock");
        when(list.get(intThat(arg -> arg >= 10))).thenReturn("My Name");

        assertAll(
                () -> assertThat(list.get(1), equalTo("Mockito")),
                () -> assertThat(list.get(2), equalTo("Mockito")),

                () -> assertThat(list.get(3), equalTo("Power_mock")),
                () -> assertThat(list.get(9), equalTo("Power_mock")),

                () -> assertThat(list.get(11), equalTo("My Name"))
        );
    }

    @Test
    void testArgumentMatcherWarning(@Mock Map<String, String> map) {
        when(map.getOrDefault(anyString(), anyString())).thenReturn("I am value");
        assertThat(map.getOrDefault("Hello", "World"), is(equalTo("I am value")));
    }

    @Test
    void testCustomArgumentMatcher(@Mock List<String> list) {
        // stabbing
        when(list.get(intThat(closedRange(0, 10)))).thenReturn("Mockito");
        when(list.get(intThat(closedRange(11, 20)))).thenReturn("PowerMock");
        when(list.get(intThat(closedRange(21, Integer.MAX_VALUE)))).thenReturn("MaxMock");

        // assertion
        assertAll(
                () -> assertThat(list.get(5), equalTo("Mockito")),
                () -> assertThat(list.get(15), equalTo("PowerMock")),
                () -> assertThat(list.get(25), equalTo("MaxMock"))
        );
    }


}