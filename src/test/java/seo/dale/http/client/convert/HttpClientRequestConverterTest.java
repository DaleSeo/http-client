package seo.dale.http.client.convert;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import seo.dale.http.client.model.HttpClientRequest;
import seo.dale.http.client.model.Post;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpClientRequestConverterTest {

	@Test
	public void testGivenNothing() {
		HttpClientRequest request = new HttpClientRequest();
		HttpClientRequestConverter<Void> converter = new HttpClientRequestConverter<>();
		HttpEntity<Void> httpEntity = converter.convert(request);
		System.out.println(httpEntity);
	}

	@Test
	public void testGivenHeaders() {
		Map<String, List<String>> headers = new HashMap<>();
		headers.put("Content-Type", Collections.singletonList(MediaType.APPLICATION_JSON_UTF8.toString()));
		headers.put("custom", Arrays.asList("A", "B", "C"));

		HttpClientRequest request = new HttpClientRequest(headers);

		HttpClientRequestConverter<Void> converter = new HttpClientRequestConverter<>();
		HttpEntity<Void> httpEntity = converter.convert(request);
		System.out.println(httpEntity);

		HttpHeaders httpHeaders = httpEntity.getHeaders();
		assertThat(httpHeaders.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
		assertThat(httpHeaders.getFirst("custom")).isEqualTo("A");
	}

	@Test
	public void testGivenBody() {
		Post body = new Post();
		body.setId(1);
		body.setTitle("foo");
		body.setBody("bar");
		body.setUserId(1);

		HttpClientRequest request = new HttpClientRequest(body);

		HttpClientRequestConverter<Post> converter = new HttpClientRequestConverter<>();
		HttpEntity<Void> httpEntity = converter.convert(request);
		System.out.println(httpEntity);

		assertThat(httpEntity.getBody()).isEqualTo(body);
	}

	@Test
	public void testGivenHeadersAndBody() {
		Map<String, List<String>> headers = new HashMap<>();
		headers.put("Content-Type", Collections.singletonList(MediaType.APPLICATION_JSON_UTF8.toString()));
		headers.put("custom", Arrays.asList("A", "B", "C"));

		Post body = new Post();
		body.setId(1);
		body.setTitle("foo");
		body.setBody("bar");
		body.setUserId(1);

		HttpClientRequest request = new HttpClientRequest(headers, body);

		HttpClientRequestConverter<Post> converter = new HttpClientRequestConverter<>();
		HttpEntity<Void> httpEntity = converter.convert(request);
		System.out.println(httpEntity);

		HttpHeaders httpHeaders = httpEntity.getHeaders();
		assertThat(httpHeaders.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
		assertThat(httpHeaders.getFirst("custom")).isEqualTo("A");
		assertThat(httpEntity.getBody()).isEqualTo(body);
	}
}