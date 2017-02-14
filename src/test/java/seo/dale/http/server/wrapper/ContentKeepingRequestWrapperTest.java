package seo.dale.http.server.wrapper;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentKeepingRequestWrapperTest {

	@Test
	public void testGivenJson() throws Exception {
		String content = "{\n" +
				"  \"id\": 123,\n" +
				"  \"name\": \"Dale Seo\",\n" +
				"  \"email\": \"dale.seo@gmail.com\"\n" +
				"}";

		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/test");
		request.setContent(content.getBytes());
		request.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

		String contentReadFromRequest = StreamUtils.copyToString(request.getInputStream(), Charset.forName(request.getCharacterEncoding()));
		System.out.println("# contentReadFromRequest : " + contentReadFromRequest);

		ContentKeepingRequestWrapper wrapper = new ContentKeepingRequestWrapper(request);

		String contentReadFromWrapper = StreamUtils.copyToString(wrapper.getInputStream(), Charset.forName(wrapper.getCharacterEncoding()));
		System.out.println("# contentReadFromWrapper : " + contentReadFromWrapper);

		byte[] contentAsByteArray = wrapper.getContentAsByteArray();
		System.out.println("# contentAsByteArray : " + Arrays.toString(contentAsByteArray));
		assertThat(contentAsByteArray).isEqualTo(content.getBytes(wrapper.getCharacterEncoding()));

		String contentAsString = wrapper.getContentAsString();
		System.out.println("# contentAsString : " + contentAsString);
		assertThat(contentAsString).isEqualTo(content);
	}

	@Test
	public void testGivenFormUrlEncoded() throws Exception {
		String content = "category=app+game%2Bmusic&productId=P12345&key=val&key=%EA%B0%92";

		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/test");
		request.setContent(content.getBytes());
		request.setContentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		String contentReadFromRequest = StreamUtils.copyToString(request.getInputStream(), Charset.forName(request.getCharacterEncoding()));
		System.out.println("# contentReadFromRequest : " + contentReadFromRequest);

		ContentKeepingRequestWrapper wrapper = new ContentKeepingRequestWrapper(request);

		String contentReadFromWrapper = StreamUtils.copyToString(wrapper.getInputStream(), Charset.forName(wrapper.getCharacterEncoding()));
		System.out.println("# contentReadFromWrapper : " + contentReadFromWrapper);

		byte[] contentAsByteArray = wrapper.getContentAsByteArray();
		System.out.println("# contentAsByteArray : " + Arrays.toString(contentAsByteArray));
		assertThat(contentAsByteArray).isEqualTo(content.getBytes(wrapper.getCharacterEncoding()));

		String contentAsString = wrapper.getContentAsString();
		System.out.println("# contentAsString : " + contentAsString);
		assertThat(contentAsString).isEqualTo(content);
	}
	
}