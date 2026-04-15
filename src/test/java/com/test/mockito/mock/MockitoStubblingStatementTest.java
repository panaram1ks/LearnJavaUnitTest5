package com.test.mockito.mock;

import com.test.mockito.mainproject.mockito.GoogleCloudStorageService;
import com.test.mockito.mainproject.mockito.PartialService;
import com.test.mockito.mainproject.mockito.StorageService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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

    @DisplayName("when...thenThrow / doThrow...when")
    @Nested
    class NestedWhenThenThrowAndDoThrowWhenStatement {

        @Mock
        private List<String> list;

        @Test
        void testWhenThenThrowStatement() {
            when(list.get(0)).thenThrow(RuntimeException.class);
            when(list.get(1)).thenThrow(new RuntimeException());
            final Executable executable0 = () -> list.get(0);
            final Executable executable1 = () -> list.get(1);
//            Assertions.assertThrows(RuntimeException.class, executable0);
//            Assertions.assertThrows(RuntimeException.class, executable1);
            Assertions.assertAll(
                    () -> Assertions.assertThrows(RuntimeException.class, executable0),
                    () -> Assertions.assertThrows(RuntimeException.class, executable1)
            );
        }

        @Test
        void testDoThrowWhenStatement() {
            doThrow(NullPointerException.class).when(list).get(0);
            doThrow(IndexOutOfBoundsException.class).when(list).get(1);
            final Executable executable0 = () -> list.get(0);
            final Executable executable1 = () -> list.get(1);
            Assertions.assertThrows(NullPointerException.class, executable0);
            Assertions.assertThrows(IndexOutOfBoundsException.class, executable1);
        }

        @Test
        void testVoidMethod() throws IOException {
            GoogleCloudStorageService googleCloudStorageServiceMock = mock(GoogleCloudStorageService.class);
//            when(googleCloudStorageServiceMock.store(any(byte[].class))).thenThrow(new IOException()); // так делать не разрешено
            doThrow(IOException.class).when(googleCloudStorageServiceMock).store(any(byte[].class));
            final Executable executable = () -> googleCloudStorageServiceMock.store(new byte[]{0x1, 0x2});
            Assertions.assertThrows(IOException.class, executable);

//            doThrow(Exception.class).when(list).get(0); // Checked Exception is not allowed here!
//            Executable executable1 = () -> list.get(0);
//            Assertions.assertThrows(Exception.class, executable1);
        }

    }

    @DisplayName("doAnswer...when / when...thenAnswer")
    @Nested
    class NestedDoAnswerWhenAndThenAnswerWhen {
        private final Answer<String> answer = invocationOnMock -> {
            Integer index = invocationOnMock.getArgument(0, Integer.class);
            return "Mockito:" + index;
        };

        @Mock
        private List<String> list;

        @Test
        void testWhenThenAnswerStatement() {
//            when(list.get(anyInt())).thenAnswer(answer);
            doAnswer(answer).when(list).get(anyInt());
            Assertions.assertEquals("Mockito:1", list.get(1));
            Assertions.assertAll(
                    () -> assertThat(list.get(0), is(equalTo("Mockito:0"))),
                    () -> assertThat(list.get(5), is(equalTo("Mockito:5"))),
                    () -> assertThat(list.get(12), is(equalTo("Mockito:12")))
            );
        }

    }

    @DisplayName("when...thenCallRealMethod / doCallRealMethod...when")
    @Nested
    class NestedCallRealMethod {
        @Mock
        private LinkedList<String> list;

        // partial stabling
        @Test
        void simpleTest() {
            when(list.add(anyString())).thenCallRealMethod();
            when(list.size()).thenCallRealMethod();

            list.add("one");
            list.add("two");

            assertThat(list.size(), equalTo(2));
            assertThat(list.get(0), nullValue());
        }
    }


    @Nested
    class NestedPartialServiceTest {

        @Mock
        private PartialService partialService;

        @Test
        void testWhenThenCallRealMethod() {
//            doCallRealMethod().when(partialService).getFromExternal();
            when(partialService.getRandom()).thenCallRealMethod();
            when(partialService.getFromExternal()).thenReturn(5);

            int random = partialService.getRandom();
            int fromExternal = partialService.getFromExternal();

            assertThat(fromExternal, equalTo(5));
            assertThat(random, lessThan(100));
        }
    }

    @DisplayName("return multiple values at one stabling statement")
    @Nested
    class NestedMultipleReturnValues {

        @Test
        void testReturnMultipleValues(@Mock List<String> list) {
//            when(list.get(0)).thenReturn("Hello", "Junit", "Mockito");
            doReturn("Hello", "Junit", "Mockito").when(list).get(0);
            Assertions.assertAll(
                    () -> assertThat(list.get(0), is(equalTo("Hello"))),
                    () -> assertThat(list.get(0), is(equalTo("Junit"))),
                    () -> assertThat(list.get(0), is(equalTo("Mockito"))),
                    () -> assertThat(list.get(0), is(equalTo("Mockito")))
            );
        }

    }


}