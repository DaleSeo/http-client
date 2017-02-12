package seo.dale.http.client.template;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import seo.dale.http.client.intercept.HeaderInterceptor;
import seo.dale.http.client.resolve.BaseUrlResolver;
import seo.dale.http.client.resolve.HeaderResolver;
import seo.dale.http.client.url.BaseUrlUriTemplateHandler;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestTemplateConfigurerTest {

	private RestTemplate restTemplate;
	private RestTemplateConfigurer configurer;

	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
		configurer = new RestTemplateConfigurer(restTemplate);
	}

	@Test
	public void testConfigureBaseUrlResolver() {
		BaseUrlResolver baseUrlResolver = () -> "https://jsonplaceholder.typicode.com";
		configurer.configure(baseUrlResolver);

		assertThat(restTemplate.getUriTemplateHandler()).isInstanceOf(BaseUrlUriTemplateHandler.class);
	}

	@Test
	public void testConfigureHeaderResolver() {
		HeaderResolver headerResolver = new HeaderResolver() {
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
		configurer.configure(headerResolver);

		assertThat(restTemplate.getInterceptors().get(0)).isInstanceOf(HeaderInterceptor.class);
	}

}