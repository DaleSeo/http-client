package seo.dale.http.client.template;

import org.junit.Test;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;
import seo.dale.http.client.error.HttpClientErrorHandler;
import seo.dale.http.client.intercept.LoggingInterceptor;
import seo.dale.http.client.log.HttpClientLoggingConfig;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestTemplateBuilderTest {

	@Test
	public void testDefault() {
		RestTemplate restTemplate = new RestTemplateBuilder().build();

		assertThat(restTemplate.getRequestFactory()).isInstanceOf(SimpleClientHttpRequestFactory.class);
		assertThat(restTemplate.getUriTemplateHandler()).isInstanceOf(DefaultUriTemplateHandler.class);
		assertThat(restTemplate.getInterceptors().isEmpty()).isTrue();
		assertThat(restTemplate.getMessageConverters().size()).isEqualTo(2);
		assertThat(restTemplate.getErrorHandler()).isInstanceOf(HttpClientErrorHandler.class);
	}

	@Test
	public void testCustom() {
		List<ClientHttpRequestInterceptor> interceptors = Collections.singletonList((request, body, execution) -> null);
		List<HttpMessageConverter<?>> messageConverters = Collections.singletonList(new StringHttpMessageConverter());


		RestTemplate restTemplate = new RestTemplateBuilder()
				.interceptors(interceptors)
				.messageConverters(messageConverters)
				.build();

		assertThat(restTemplate.getInterceptors().size()).isEqualTo(1);
		assertThat(restTemplate.getMessageConverters().size()).isEqualTo(1);
	}

	@Test
	public void testContentKeeping() {
		HttpClientLoggingConfig config = HttpClientLoggingConfig.custom().build();
		List<ClientHttpRequestInterceptor> interceptors = Collections.singletonList(new LoggingInterceptor(config));

		RestTemplate restTemplate = new RestTemplateBuilder()
				.interceptors(interceptors)
				.build();

		restTemplate.setInterceptors(null); // InterceptingClientHttpRequestFactory wrapping을 막음
		assertThat(restTemplate.getRequestFactory()).isInstanceOf(BufferingClientHttpRequestFactory.class);
	}

	@Test
	public void testNotContentKeeping() {
		List<ClientHttpRequestInterceptor> interceptors = Collections.singletonList((request, body, execution) -> null);
		RestTemplate restTemplate = new RestTemplateBuilder()
				.interceptors(interceptors)
				.build();

		restTemplate.setInterceptors(null); // InterceptingClientHttpRequestFactory wrapping을 막음
		assertThat(restTemplate.getRequestFactory()).isNotInstanceOf(BufferingClientHttpRequestFactory.class);
	}

}