package seo.dale.http.log.server.builder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import seo.dale.http.log.common.LogConstants;
import seo.dale.http.log.server.extractor.RequestLogInfoExtractor;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RequestLogMessageBuilderCurlTest {

	private RequestLogMessageBuilderCurl builder;

	private RequestLogInfoExtractor requestLogInfoExtractor;

	@Before
	public void setUp() throws Exception {
		builder = new RequestLogMessageBuilderCurl(true, 30);
	}

	@Test
	public void testBuildForPost() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("User-Agent", "Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
		headers.add("Host", "www.someclient.com");

		String content = "{\n" +
				"  \"id\": 123,\n" +
				"  \"name\": \"Dale Seo\",\n" +
				"  \"email\": \"dale.seo@sk.com\"\n" +
				"}";
		String body = content.substring(0, 30);

		requestLogInfoExtractor = Mockito.mock(RequestLogInfoExtractor.class);
		when(requestLogInfoExtractor.extractMethod()).thenReturn("POST");
		when(requestLogInfoExtractor.extractUrl()).thenReturn("/test");
		when(requestLogInfoExtractor.extractQueryString()).thenReturn("key=val");
		when(requestLogInfoExtractor.extractProtocol()).thenReturn("HTTP/1.1");
		when(requestLogInfoExtractor.extractHeaders()).thenReturn(headers);
		when(requestLogInfoExtractor.extractBody(30)).thenReturn(body);

		String logMessage = builder.build(requestLogInfoExtractor);
		System.out.println(logMessage);

		Scanner scanner = new Scanner(new ByteArrayInputStream(logMessage.getBytes()));
		assertEquals("[[" + LogConstants.SERVER_REQUEST_LOG_PREFIX + "]]", scanner.nextLine());
		assertEquals("POST /test?key=val HTTP/1.1", scanner.nextLine());
		assertEquals("> User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)", scanner.nextLine());
		assertEquals("> Host: www.someclient.com", scanner.nextLine());
		assertEquals("{", scanner.nextLine());
		assertEquals("  \"id\": 123,", scanner.nextLine());
		assertEquals("  \"name\": \"Dale", scanner.nextLine());
		scanner.close();

		verify(requestLogInfoExtractor).extractUrl();
		verify(requestLogInfoExtractor).extractQueryString();
		verify(requestLogInfoExtractor).extractProtocol();
		verify(requestLogInfoExtractor).extractHeaders();
		verify(requestLogInfoExtractor).extractBody(30);
	}

	@Test
	public void testBuildForGet() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("User-Agent", "Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
		headers.add("Host", "www.someclient.com");

		requestLogInfoExtractor = Mockito.mock(RequestLogInfoExtractor.class);
		when(requestLogInfoExtractor.extractMethod()).thenReturn("GET");
		when(requestLogInfoExtractor.extractUrl()).thenReturn("/test");
		when(requestLogInfoExtractor.extractQueryString()).thenReturn("key=val");
		when(requestLogInfoExtractor.extractProtocol()).thenReturn("HTTP/1.1");
		when(requestLogInfoExtractor.extractHeaders()).thenReturn(headers);
		when(requestLogInfoExtractor.extractBody(30)).thenReturn("");

		String logMessage = builder.build(requestLogInfoExtractor);
		System.out.println(logMessage);

		Scanner scanner = new Scanner(new ByteArrayInputStream(logMessage.getBytes()));
		assertEquals("[[" + LogConstants.SERVER_REQUEST_LOG_PREFIX + "]]", scanner.nextLine());
		assertEquals("GET /test?key=val HTTP/1.1", scanner.nextLine());
		assertEquals("> User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)", scanner.nextLine());
		assertEquals("> Host: www.someclient.com", scanner.nextLine());
		scanner.close();

		verify(requestLogInfoExtractor).extractUrl();
		verify(requestLogInfoExtractor).extractQueryString();
		verify(requestLogInfoExtractor).extractProtocol();
		verify(requestLogInfoExtractor).extractHeaders();
		verify(requestLogInfoExtractor).extractBody(30);
	}

}