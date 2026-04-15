package com.test.mockito.mainproject.mockito;

import java.io.IOException;

public class StorageService {

    private GoogleCloudStorageService googleCloudStorageService;

    public StorageService(GoogleCloudStorageService googleCloudStorageService) {
        this.googleCloudStorageService = googleCloudStorageService;
    }

    public boolean uploadToCloud(byte[] data) {
        try {
            if (data != null && data.length > 0) {
                googleCloudStorageService.store(data);
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}