package seo.dale.http.client.url;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseUrlUriTemplateHandlerTest {

	private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

	private BaseUrlUriTemplateHandler uriTemplateHandler;

	@Before
	public void setUp() {
		uriTemplateHandler = new BaseUrlUriTemplateHandler(() -> BASE_URL);
	}

	@Test
	public void testExpandGivenSubUrl() {
		URI uri = uriTemplateHandler.expand("/posts/{id}", 1);
		System.out.println(uri);
		assertThat(uri.toString()).isEqualTo(BASE_URL + "/posts/1");
	}

	@Test
	public void testExpandGivenFullUrl() {
		URI uri = uriTemplateHandler.expand(BASE_URL + "/posts/{id}", 1);
		System.out.println(uri);
		assertThat(uri.toString()).isEqualTo(BASE_URL + "/posts/1");
	}

}