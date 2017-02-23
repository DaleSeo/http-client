package seo.dale.http.client.url;

import org.junit.Before;
import org.junit.Test;
import seo.dale.http.client.model.HttpClientUrl;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlStringBuilderTest {

	private UrlStringBuilder urlStringBuilder;

	@Before
	public void setUp() {
		urlStringBuilder = new UrlStringBuilder();
	}

	@Test
	public void testBuildUrlWithBaseUrl() throws Exception {
		HttpClientUrl url = new HttpClientUrl.Builder().path("https://jsonplaceholder.typicode.com/posts/{no}").build();
		String urlString = urlStringBuilder.buildUrl(url);
//		System.out.println(urlString);
		assertThat(urlString).isEqualTo("https://jsonplaceholder.typicode.com/posts/{no}");
	}

	@Test
	public void testBuildUrlWithoutBaseUrl() throws Exception {
		HttpClientUrl url = new HttpClientUrl.Builder().path("/posts/{no}").build();
		String urlString = urlStringBuilder.buildUrl(url);
//		System.out.println(urlString);
		assertThat(urlString).isEqualTo("/posts/{no}");
	}

	@Test
	public void testBuildUrlGivenGet() throws Exception {
		HttpClientUrl url = new HttpClientUrl.Builder()
				.scheme(HttpClientUrl.Scheme.HTTPS)
				.host("jsonplaceholder.typicode.com")
				.path("/posts/{no}")
				.build();
		String urlString = urlStringBuilder.buildUrl(url);
//		System.out.println(urlString);
		assertThat(urlString).isEqualTo("https://jsonplaceholder.typicode.com/posts/{no}");
	}

}