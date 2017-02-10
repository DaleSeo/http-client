package seo.dale.http.client.intercept;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HeaderInterceptorTest {

	@Test
	public void test() throws IOException {
		HeaderInterceptor interceptor = new HeaderInterceptor();
		HttpRequest request = new MockClientHttpRequest();
		ClientHttpRequestExecution execution = (req, body) -> new MockClientHttpResponse("{}".getBytes(), HttpStatus.OK);
		interceptor.intercept(request, "{}".getBytes(), execution);

		HttpHeaders headers = request.getHeaders();
		System.out.println(headers);
		assertThat(headers.getFirst("UUID")).isNotBlank();
		assertThat(headers.getAccept()).containsOnly(MediaType.ALL);
		assertThat(headers.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
	}

}