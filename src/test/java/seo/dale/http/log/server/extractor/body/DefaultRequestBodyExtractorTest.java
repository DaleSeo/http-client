package seo.dale.http.log.server.extractor.body;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import seo.dale.http.server.wrapper.ContentKeepingRequestWrapper;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultRequestBodyExtractorTest {

	private DefaultRequestBodyExtractor extractor;

	private ContentKeepingRequestWrapper wrapper;

	private String content = "{\n" +
			"  \"id\": 123,\n" +
			"  \"name\": \"Dale Seo\",\n" +
			"  \"email\": \"dale.seo@gmail.com\"\n" +
			"}";

	@Before
	public void setUp() throws Exception {
		extractor = new DefaultRequestBodyExtractor();

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContent(content.getBytes());

		wrapper = new ContentKeepingRequestWrapper(request);
	}

	@Test
	public void testExtractPayloadSome() throws Exception {
		String body = extractor.extractBody(wrapper, 20);
		System.out.println(body);
		assertThat(body.length()).isEqualTo(20);
	}


	@Test
	public void testExtractPayloadAll() throws Exception {
		String body = extractor.extractBody(wrapper, Integer.MAX_VALUE);
		System.out.println(body);
		assertThat(body.length()).isEqualTo(content.length());
	}

}