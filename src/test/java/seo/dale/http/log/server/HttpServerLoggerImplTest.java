package seo.dale.http.log.server;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.mockito.Mockito.mock;

public class HttpServerLoggerImplTest {

    private HttpServerLogger servletLogger;

    private Appender mockAppender;
    private ArgumentCaptor<LoggingEvent> argumentCaptor;

    @Before
    public void setUp() {
        servletLogger = new HttpServerLoggerImpl();
        mockAppender = mock(Appender.class);
        argumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);

        Logger logbackLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logbackLogger.detachAndStopAllAppenders();
        logbackLogger.addAppender(mockAppender);
    }

    @Test
    public void testLogRequest() {
        String content = "{\n" +
                "  \"id\": 123,\n" +
                "  \"name\": \"Dale Seo\",\n" +
                "  \"email\": \"dale.seo@sk.com\"\n" +
                "}";

        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/test1/test2");
        request.setQueryString("key1=val1&key2=val2");
        request.setProtocol("HTTP/1.1");

        request.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
        request.addHeader("Host", "www.someclient.com");
        request.addHeader("Content-Type", "application/json;charset=UTF-8");
        request.addHeader("Content-Length", "67");
        request.addHeader("Accept", "application/json;charset=UTF-8");
        request.setContent(content.getBytes());
    }

    @Test
    public void testLogResponse() {

    }

}