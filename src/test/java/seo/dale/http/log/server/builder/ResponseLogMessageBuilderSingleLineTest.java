package seo.dale.http.log.server.builder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import seo.dale.http.log.common.LogConstants;
import seo.dale.http.log.server.extractor.ResponseLogInfoExtractor;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ResponseLogMessageBuilderSingleLineTest {

	private ResponseLogMessageBuilderSingleLine builder;

	private ResponseLogInfoExtractor responseLogInfoExtractor;

	@Before
	public void setUp() throws Exception {
		builder = new ResponseLogMessageBuilderSingleLine(true, 30);
	}

	@Test
	public void testBuild() throws Exception {
		String content = "{\n" +
				"  \"id\": 123,\n" +
				"  \"name\": \"Dale Seo\",\n" +
				"  \"email\": \"dale.seo@sk.com\"\n" +
				"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setContentLength(content.length());

		responseLogInfoExtractor = Mockito.mock(ResponseLogInfoExtractor.class);
		Mockito.when(responseLogInfoExtractor.extractProtocol()).thenReturn("HTTP/1.1");
		Mockito.when(responseLogInfoExtractor.extractStatus()).thenReturn(HttpStatus.OK);
		Mockito.when(responseLogInfoExtractor.extractHeaders()).thenReturn(headers);
		Mockito.when(responseLogInfoExtractor.extractBody(30)).thenReturn(content.substring(0, 30));

		String logMessage = builder.build(responseLogInfoExtractor);
		System.out.println(logMessage);

		Scanner scanner = new Scanner(new ByteArrayInputStream(logMessage.getBytes()));
		Assert.assertEquals(LogConstants.SERVER_RESPONSE_LOG_PREFIX + " >> HTTP/1.1 200 OK || HEADERS > {Content-Type=[application/json;charset=UTF-8], Content-Length=[67]} || BODY > {", scanner.nextLine());
		scanner.close();

		Mockito.verify(responseLogInfoExtractor).extractProtocol();
		Mockito.verify(responseLogInfoExtractor).extractStatus();
		Mockito.verify(responseLogInfoExtractor).extractHeaders();
		Mockito.verify(responseLogInfoExtractor).extractBody(30);
	}
	
}