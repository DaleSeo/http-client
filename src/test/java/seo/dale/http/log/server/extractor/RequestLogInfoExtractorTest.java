package seo.dale.http.log.server.extractor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import seo.dale.http.server.wrapper.ContentKeepingRequestWrapper;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RequestLogInfoExtractorTest {

	private RequestLogInfoExtractor extractor;

	@Before
	public void setUp() throws Exception {
		String content = "{\n" +
				"  \"id\": 123,\n" +
				"  \"name\": \"Dale Seo\",\n" +
				"  \"email\": \"dale.seo@sk.com\"\n" +
				"}";

		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/test1/test2");
		request.setQueryString("key1=val1&key2=val2");
		request.setProtocol("HTTP/1.1");

		request.setRemoteAddr("116.117.118.119");
		request.setRemoteHost("www.someclient.com");
		request.setRemotePort(8080);

		request.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
		request.addHeader("Host", "www.someclient.com");
		request.addHeader("Content-Type", "application/json;charset=UTF-8");
		request.addHeader("Content-Length", "67");
		request.addHeader("Accept", "application/json;charset=UTF-8");
		request.setContent(content.getBytes());

		ContentKeepingRequestWrapper wrapper = new ContentKeepingRequestWrapper(request);
		extractor = new RequestLogInfoExtractor(wrapper);
	}

	@Test
	public void testExtractMethod() throws Exception {
		assertEquals("POST", extractor.extractMethod());
	}

	@Test
	public void testExtractUrl() throws Exception {
		assertTrue(extractor.extractUrl().endsWith("/test1/test2"));
	}

	@Test
	public void testExtractQueryString() throws Exception {
		assertEquals("key1=val1&key2=val2", extractor.extractQueryString());
	}

	@Test
	public void testExtractProtocol() throws Exception {
		assertEquals("HTTP/1.1", extractor.extractProtocol());
	}

	@Test
	public void testExtractRemoteAddr() throws Exception {
		assertEquals("116.117.118.119", extractor.extractRemoteAddr());
	}

	@Test
	public void testExtractRemoteHost() throws Exception {
		assertEquals("www.someclient.com", extractor.extractRemoteHost());
	}

	@Test
	public void testExtractRemotePort() throws Exception {
		assertEquals("8080", extractor.extractRemotePort());
	}

	@Test
	public void testExtractHeaders() throws Exception {
		HttpHeaders headers = extractor.extractHeaders();
		System.out.println(headers);
		Assert.assertEquals(5, headers.size());
	}

	@Test
	public void testExtractPayload() throws Exception {
		String body = extractor.extractBody(20);
		System.out.println(body);
		Assert.assertEquals(20, body.length());
	}

}