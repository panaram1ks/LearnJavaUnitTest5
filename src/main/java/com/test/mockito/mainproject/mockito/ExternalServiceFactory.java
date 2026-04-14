package com.test.mockito.mainproject.mockito;

public class ExternalServiceFactory {

    public ExternalService createExternalService(){
        throw new RuntimeException();
    }
}