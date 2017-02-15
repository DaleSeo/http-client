package seo.dale.http.log.server;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import seo.dale.http.server.wrapper.ContentKeepingRequestWrapper;
import seo.dale.http.server.wrapper.ContentKeepingResponseWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HttpServerLoggerImplTest {

	private HttpServerLoggerImpl HttpServerLoggerImpl;
	private Appender mockAppender;
	private ArgumentCaptor<LoggingEvent> argumentCaptor;

	@Before
	public void setUp() {
		HttpServerLoggerImpl = new HttpServerLoggerImpl(
				HttpServerLoggerConfig.custom()
						.logger(LoggerFactory.getLogger("seo.dale.http"))
						.logLevel("info")
						.shouldIncludeHeaders(true)
						.maxBodyLength(100)
						.style(HttpServerLogStyle.CURL)
						.build()
		);
		mockAppender = mock(Appender.class);
		argumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);

		Logger logbackLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		logbackLogger.detachAndStopAllAppenders();
		logbackLogger.addAppender(mockAppender);
	}

	@Test
	public void logRequest() {
		String content = "{\n" +
				"  \"id\": 123,\n" +
				"  \"name\": \"Dale Seo\",\n" +
				"  \"email\": \"dale.seo@gmail.com\"\n" +
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

		// Log Request
		HttpServerLoggerImpl.logRequest(new ContentKeepingRequestWrapper(request));

		verify(mockAppender).doAppend(argumentCaptor.capture());

		LoggingEvent loggingEvent = argumentCaptor.getValue();

		String logMessage = loggingEvent.getFormattedMessage();

		System.out.println(logMessage);

		Scanner scanner = new Scanner(new ByteArrayInputStream(logMessage.getBytes()));
		assertEquals("[[SERVER_REQUEST_LOGGING]]", scanner.nextLine());
		assertEquals("POST http://localhost/test1/test2?key1=val1&key2=val2 HTTP/1.1", scanner.nextLine());
		assertEquals("> User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)", scanner.nextLine());
		assertEquals("> Host: www.someclient.com", scanner.nextLine());
		assertEquals("> Content-Type: application/json;charset=UTF-8", scanner.nextLine());
		assertEquals("> Content-Length: 67", scanner.nextLine());
		assertEquals("> Accept: application/json;charset=UTF-8", scanner.nextLine());
		assertEquals("{", scanner.nextLine());
		assertEquals("  \"id\": 123,", scanner.nextLine());
		assertEquals("  \"name\": \"Dale Seo\",", scanner.nextLine());
		assertEquals("  \"email\": \"dale.seo@gmail.com\"", scanner.nextLine());
		assertEquals("}", scanner.nextLine());
		scanner.close();
	}

	@Test
	public void logResponse() throws IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setProtocol("HTTP/1.1");

		String content = "{\n" +
				"  \"errorCode\": 100001,\n" +
				"  \"errorMessage\": \"Unknown Problem\"\n" +
				"}";

		ContentKeepingResponseWrapper response = new ContentKeepingResponseWrapper(new MockHttpServletResponse());
		response.setStatus(200);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setContentLength(content.length());

		PrintWriter out = response.getWriter();
		out.print(content);
		out.flush();
		out.close();

		// Log Response
		HttpServerLoggerImpl.logResponse(request, response);

		verify(mockAppender).doAppend(argumentCaptor.capture());

		LoggingEvent loggingEvent = argumentCaptor.getValue();

		String logMessage = loggingEvent.getFormattedMessage();

		System.out.println(logMessage);

		Scanner scanner = new Scanner(new ByteArrayInputStream(logMessage.getBytes()));
		assertEquals("[[SERVER_RESPONSE_LOGGING]]", scanner.nextLine());
		assertEquals("HTTP/1.1 200 OK", scanner.nextLine());
		assertEquals("> Content-Type: application/json;charset=UTF-8", scanner.nextLine());
		assertEquals("> Content-Length: 62", scanner.nextLine());
		assertEquals("{", scanner.nextLine());
		assertEquals("  \"errorCode\": 100001,", scanner.nextLine());
		assertEquals("  \"errorMessage\": \"Unknown Problem\"", scanner.nextLine());
		assertEquals("}", scanner.nextLine());
		scanner.close();
	}

	@After
	public void tearDown() {
		LoggingEvent loggingEvent = (LoggingEvent) argumentCaptor.getValue();
		assertEquals("seo.dale.http", loggingEvent.getLoggerName());
		assertEquals(Level.INFO, loggingEvent.getLevel());
	}

}