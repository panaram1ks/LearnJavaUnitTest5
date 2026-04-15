package com.test.mockito.mock;

import com.test.mockito.mainproject.mockito.GoogleCloudStorageService;
import com.test.mockito.mainproject.mockito.StorageService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Mockito stabling statement")
class MockitoStablingStatementTest {

    @DisplayName("when...thenReturn / doReturn...when")
    @Nested
    class NestedClassWhenThenReturnAndDoReturnWhen {

        @Mock
        private List<String> list;

        @Test
        void testWhenThenReturnStatement() {
            String exampleStr = "first element";
            when(list.get(0)).thenReturn(exampleStr);

            assertThat(list.get(0), is(equalTo(exampleStr)));
            assertThat(list.get(1), nullValue());
        }

        @Test
        void testDoReturnWhenStatement() {
            String exampleStr = "first element";
            doReturn(exampleStr).when(list).get(0);
            doReturn(null).when(list).get(1);

            assertThat(list.get(0), is(equalTo(exampleStr)));
            assertThat(list.get(1), nullValue());
        }
    }

    @DisplayName("doNothing...when")
    @Nested
    class NestedDoNothingWhen {

        @Mock
        private List<String> list;

        @Test
        void testDoNothingWhenStatement() {
            doNothing().when(list).clear();
            list.clear();
            list.clear();

            verify(list, times(2)).clear();
        }

        @Disabled
        @Test
        void testDoNothingWhenStatement2() {
            doNothing().when(list).get(0);
            list.get(0);
        }
    }

    @DisplayName("use service like real")
    @Nested
    class NestedDoNothingWhenUseService {

        @Mock
        private GoogleCloudStorageService googleCloudStorageService;

        private StorageService storageService;

        @BeforeEach
        void setUp() throws IOException {
            this.storageService = new StorageService(googleCloudStorageService);
            lenient().doNothing().when(googleCloudStorageService).store(any(byte[].class));
        }

        @Test
        void testUploadToCloudSuccess() throws IOException {
            boolean result = this.storageService.uploadToCloud(new byte[]{0x1, 0x2});
            assertThat(result, is(equalTo(true)));
            verify(googleCloudStorageService).store(any(byte[].class));
        }

        @Test
        void testUploadToCloudFailure() throws IOException {
            boolean result = this.storageService.uploadToCloud(null);
            assertThat(result, is(equalTo(false)));
            verify(googleCloudStorageService, times(0)).store(any(byte[].class));
        }

    }

}