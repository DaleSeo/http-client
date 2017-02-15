package seo.dale.http.log.server.extractor.body;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class FormUrlencodedRequestBodyExtractorTest {

	private FormUrlencodedRequestBodyExtractor extractor;

	private MockHttpServletRequest request;

	@Before
	public void setUp() throws Exception {
		extractor = new FormUrlencodedRequestBodyExtractor();

		request = new MockHttpServletRequest();
		request.setParameter("id", "123");
		request.setParameter("name", "서대영");
		request.setParameter("email", new String[]{"dale.seo@gmail.com", "dale.seo@daum.net"});
	}

	@Test
	public void testExtractPayloadSome() throws Exception {
		String body = extractor.extractBody(request, 13);
		System.out.println(body);
		assertThat(body).isEqualTo("id=123&name=서");
	}

	@Test
	public void testExtractPayloadAll() throws Exception {
		String body = extractor.extractBody(request, Integer.MAX_VALUE);
		System.out.println(body);
		assertThat(body).isEqualTo("id=123&name=서대영&email=dale.seo@gmail.com&email=dale.seo@daum.net");
	}

}