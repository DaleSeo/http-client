package seo.dale.http.client.invoke;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import seo.dale.http.client.intercept.LoggingInterceptor;
import seo.dale.http.client.log.HttpClientLoggingConfig;
import seo.dale.http.client.model.*;
import seo.dale.http.client.template.RestTemplateBuilder;

import java.util.*;

public class HttpClientInvokerSpringTest {

	private HttpClientInvokerSpring httpClientInvoker;

	@Before
	public void setUp() {
		HttpClientLoggingConfig config = HttpClientLoggingConfig
				.custom()
				.build();
		RestTemplate restTemplate = new RestTemplateBuilder()
				.interceptors(Collections.singletonList(new LoggingInterceptor(config)))
				.build();
		httpClientInvoker = new HttpClientInvokerSpring(restTemplate);
	}

	@Test
	public void test() {
		HttpClientMethod method = HttpClientMethod.GET;
		HttpClientUrl url = new HttpClientUrl.Builder()
				.scheme(HttpClientUrl.Scheme.HTTPS)
				.host("jsonplaceholder.typicode.com")
				.path("/posts/1")
				.build();
		Map<String, List<String>> headers = new HashMap<>();
		headers.put("User-Agent", Collections.singletonList("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36"));

		HttpClientRequest<Void> request = new HttpClientRequest(headers);

		HttpClientResponse<Post> response = httpClientInvoker.invoke(method, url, request, Post.class);
		System.out.println(response);
	}

}