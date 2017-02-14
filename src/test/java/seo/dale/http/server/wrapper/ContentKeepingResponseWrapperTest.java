package seo.dale.http.server.wrapper;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentKeepingResponseWrapperTest {

	@Test
	public void getContentAsString() throws IOException {
		String content = "{\n" +
				"  \"errorCode\": 100001,\n" +
				"  \"errorMessage\": \"알 수 없는 오류\"\n" +
				"}";

		MockHttpServletResponse response = new MockHttpServletResponse();
		response.setStatus(200);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setContentLength(content.length());
		ContentKeepingResponseWrapper wrapper = new ContentKeepingResponseWrapper(response);

		// write raw response
		ServletOutputStream outFromResponse = response.getOutputStream();
		IOUtils.write(content, outFromResponse);

		String contentWrittenViaResponse = wrapper.getContentAsString();
		assertThat(contentWrittenViaResponse.isEmpty()).isTrue();

		// write response wrapper
		ServletOutputStream outFromWrapper = wrapper.getOutputStream();
		IOUtils.write(content, outFromWrapper);

		String contentWrittenViaWrapper = wrapper.getContentAsString();
		assertThat(contentWrittenViaWrapper).isEqualTo(content);
	}

}