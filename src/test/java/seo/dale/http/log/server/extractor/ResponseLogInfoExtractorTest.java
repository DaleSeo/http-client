package seo.dale.http.log.server.extractor;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import seo.dale.http.server.wrapper.ContentKeepingResponseWrapper;

import static org.junit.Assert.assertEquals;

public class ResponseLogInfoExtractorTest {

	private ResponseLogInfoExtractor extractor;

	@Before
	public void setUp() throws Exception {
		String content = "{\n" +
				"  \"id\": 123,\n" +
				"  \"name\": \"Dale Seo\",\n" +
				"  \"email\": \"dale.seo@sk.com\"\n" +
				"}";

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setProtocol("HTTP/1.1");

		MockHttpServletResponse response = new MockHttpServletResponse();
		ContentKeepingResponseWrapper wrapper = new ContentKeepingResponseWrapper(response);
		wrapper.setStatus(200);
		wrapper.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		wrapper.setContentLength(content.length());

		IOUtils.write(content, wrapper.getOutputStream());

		extractor = new ResponseLogInfoExtractor(request, wrapper);
	}

	@Test
	public void extractProtocol() {
		assertEquals("HTTP/1.1", extractor.extractProtocol());
	}

	@Test
	public void extractStatus() {
		assertEquals(HttpStatus.OK, extractor.extractStatus());
	}

	@Test
	public void extractHeaders() {
		HttpHeaders headers = extractor.extractHeaders();
		System.out.println(headers);
		Assert.assertEquals(2, headers.size());
	}

	@Test
	public void extractPayload() {
		String body = extractor.extractBody(20);
		System.out.println(body);
		Assert.assertEquals(20, body.length());
	}

}