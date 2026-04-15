package com.test.mockito.mainproject.mockito;

import java.util.concurrent.ThreadLocalRandom;

public class PartialService {

    public int getRandom() {
        return ThreadLocalRandom.current().nextInt(100);
    }

    public int getFromExternal() {
        throw new RuntimeException();
    }

}