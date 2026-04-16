package com.test.mockito.mainproject.mockito;

import org.mockito.ArgumentMatcher;


public class ClosedRangeArgumentMatcher implements ArgumentMatcher<Integer> {

    private final int begin;
    private final int end;

    public ClosedRangeArgumentMatcher(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public static ClosedRangeArgumentMatcher closedRange(int begin, int end) {
        return new ClosedRangeArgumentMatcher(begin, end);
    }

    @Override
    public boolean matches(Integer index) {
        return index >= begin && index <= end;
    }

    @Override
    public String toString() {
        return "ClosedRangeArgumentMatcher{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }
}