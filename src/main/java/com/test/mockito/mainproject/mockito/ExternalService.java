package com.test.mockito.mainproject.mockito;


public class ExternalService {

    public String getValue() {
        throw new RuntimeException();
    }
}