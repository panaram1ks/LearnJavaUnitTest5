package com.test.mockito.mock;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;


@DisplayName("BDD Mockito")
@ExtendWith(MockitoExtension.class)
class BDDMockitoTest {

    @Test
    void testBDDStablingStatement(@Mock List<String> list) {
        //given
        given(list.get(anyInt())).willReturn("Mockito");
        //when
        String value = list.get(10);
        //then
        then(list).should(times(1)).get(10);
        MatcherAssert.assertThat(value, Matchers.equalTo("Mockito"));
    }
}