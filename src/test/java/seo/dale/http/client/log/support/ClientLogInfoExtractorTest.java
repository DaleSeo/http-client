package seo.dale.http.client.log.support;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import seo.dale.http.client.common.HttpClientTestCostants;
import seo.dale.http.client.log.HttpClientLoggerConfig;
import seo.dale.http.client.log.model.ClientRequestLogInfo;
import seo.dale.http.client.log.model.ClientResponseLogInfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static seo.dale.http.client.common.HttpClientTestCostants.RESPONSE_BODY;

public class ClientLogInfoExtractorTest {

	private ClientLoggingInfoExtractor clientLoggingInfoExtractor;

	@Before
	public void setUp() {
		clientLoggingInfoExtractor = new ClientLoggingInfoExtractor(
				HttpClientLoggerConfig.custom()
						.maxPayloadLength(30)
						.payloadPretty(true) // pretty print
						.build()
		);
	}

	@Test
	public void testExtractFromClientRequest() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		HttpRequest request = mock(HttpRequest.class);
		when(request.getMethod()).thenReturn(HttpMethod.POST);
		when(request.getURI()).thenReturn(URI.create("www.test.com"));
		when(request.getHeaders()).thenReturn(headers);

		byte[] body = HttpClientTestCostants.REQUEST_BODY.getBytes();

		ClientRequestLogInfo loggingInfo = clientLoggingInfoExtractor.extractFromClientRequest(request, body);
		assertEquals(loggingInfo.getMethod(), HttpMethod.POST);
		assertEquals(loggingInfo.getUri(), URI.create("www.test.com"));
		assertEquals(loggingInfo.getHttpHeaders(), headers);
		assertEquals(loggingInfo.getPayload(), HttpClientTestCostants.REQUEST_BODY.substring(0, 30));

		verify(request).getMethod();
		verify(request).getURI();
		verify(request).getHeaders();
	}

	@Test
	public void testExtractFromClientResponse() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		ClientHttpResponse response = mock(ClientHttpResponse.class);
		when(response.getStatusCode()).thenReturn(HttpStatus.OK);
		when(response.getHeaders()).thenReturn(headers);
		when(response.getBody()).thenReturn(new ByteArrayInputStream(RESPONSE_BODY.getBytes()));

		ClientResponseLogInfo loggingInfo = clientLoggingInfoExtractor.extractFromClientResponse(response);
		assertEquals(loggingInfo.getHttpStatus(), HttpStatus.OK);
		assertEquals(loggingInfo.getPayload(), RESPONSE_BODY.substring(0, 30));

		verify(response).getStatusCode();
		verify(response).getHeaders();
		verify(response).getBody();
	}

}