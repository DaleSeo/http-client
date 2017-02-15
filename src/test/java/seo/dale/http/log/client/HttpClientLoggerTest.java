package seo.dale.http.log.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;
import seo.dale.http.client.common.HttpClientTestCostants;
import seo.dale.http.log.client.HttpClientLogger;
import seo.dale.http.log.client.HttpClientLoggerConfig;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HttpClientLoggerTest {

	private HttpClientLogger logger;
	private Appender mockAppender;
	private ArgumentCaptor<Appender> argumentCaptor;


	@Before
	public void setUp() {
		mockAppender = mock(Appender.class);
		argumentCaptor = ArgumentCaptor.forClass(Appender.class);

		Logger logbackLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		logbackLogger.detachAndStopAllAppenders();
		logbackLogger.addAppender(mockAppender);

		HttpClientLoggerConfig config = HttpClientLoggerConfig.custom()
				.loggerName(getClass().getName())
				.logLevel("WARN")
				.maxPayloadLength(30)
				.messagePrefix("ABC")
				//.skippableOnSuccess(true)
				.skippableOnSuccess(false)
				.build();
		logger = new HttpClientLogger(config);
	}

	@Test
	public void testLogRequest() throws IOException {
		MockClientHttpRequest request = new MockClientHttpRequest(HttpMethod.POST, URI.create("http://www.test.com/api"));
		byte[] body = HttpClientTestCostants.REQUEST_BODY.getBytes();
		logger.logClientRequest(request, body);

		verify(mockAppender).doAppend(argumentCaptor.capture());

		LoggingEvent loggingEvent = (LoggingEvent) argumentCaptor.getValue();
		String logMessage = loggingEvent.getFormattedMessage();

		System.out.println(logMessage);

		Scanner scanner = new Scanner(new ByteArrayInputStream(logMessage.getBytes()));
		assertThat(scanner.nextLine()).isEqualTo("ABC_CLIENT_REQUEST_LOGGING\tPOST\thttp://www.test.com/api\tBODY{");
		assertThat(scanner.nextLine()).isEqualTo("  \"id\": 111,");
		assertThat(scanner.nextLine()).isEqualTo("  \"title\": \"Req");
	}

	@Test
	public void testLogResponse() throws IOException {
		MockClientHttpResponse response = new MockClientHttpResponse(HttpClientTestCostants.RESPONSE_BODY.getBytes(), HttpStatus.OK);
		logger.logClientResponse(response);

		verify(mockAppender).doAppend(argumentCaptor.capture());

		LoggingEvent loggingEvent = (LoggingEvent) argumentCaptor.getValue();
		String logMessage = loggingEvent.getFormattedMessage();

		System.out.println(logMessage);

		Scanner scanner = new Scanner(new ByteArrayInputStream(logMessage.getBytes()));
		assertThat(scanner.nextLine()).isEqualTo("ABC_CLIENT_RESPONSE_LOGGING\t200\tBODY{");
		assertThat(scanner.nextLine()).isEqualTo("  \"id\": 222,");
		assertThat(scanner.nextLine()).isEqualTo("  \"title\": \"Res");
	}

}