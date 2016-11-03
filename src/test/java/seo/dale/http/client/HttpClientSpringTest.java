package seo.dale.http.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import seo.dale.http.client.intercept.LoggingInterceptor;
import seo.dale.http.client.log.HttpClientLoggingConfig;
import seo.dale.http.client.resolve.BaseUrlResolver;
import seo.dale.http.client.resolve.HeaderResolver;
import seo.dale.http.client.template.RestTemplateBuilder;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
public class HttpClientSpringTest {

	@Autowired
	private HttpClient httpClient;

	@Test
	public void testGet() {

	}

	@Test
	public void testPost() {

	}

	@Test
	public void testPut() {

	}

	@Test
	public void testDelete() {

	}

	@Configuration
	public static class Config {

		@Bean
		public HttpClient sacClient(RestTemplate restTemplate, BaseUrlResolver baseUrlResolver, HeaderResolver headerResolver) {
			HttpClientSpring sacClient = new HttpClientSpring(restTemplate, baseUrlResolver, headerResolver);
			return sacClient;
		}

		@Bean
		public RestTemplate restTemplate() {
			HttpClientLoggingConfig config = HttpClientLoggingConfig
					.custom()
					.build();
			RestTemplate restTemplate = new RestTemplateBuilder()
					.interceptors(Collections.singletonList(new LoggingInterceptor(config)))
					.build();
			return restTemplate;
		}

		@Bean
		public BaseUrlResolver baseUrlResolver() {
			return () -> "https://jsonplaceholder.typicode.com";
		}

		@Bean
		public HeaderResolver headerResolver() {
			return new HeaderResolver() {
				@Override
				public String resolveAuthKey() {
					return "85f1f03";
				}

				@Override
				public MediaType resolveContentType() {
					return MediaType.APPLICATION_JSON_UTF8;
				}

				@Override
				public List<MediaType> resolveAccept() {
					return Collections.singletonList(MediaType.APPLICATION_JSON_UTF8);
				}
			};
		}

	}

}