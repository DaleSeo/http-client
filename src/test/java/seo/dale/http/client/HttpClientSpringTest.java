package seo.dale.http.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import seo.dale.http.client.intercept.LogInterceptor;
import seo.dale.http.client.log.HttpClientLoggerConfig;
import seo.dale.http.client.model.Post;
import seo.dale.http.client.resolve.BaseUrlResolver;
import seo.dale.http.client.resolve.HeaderResolver;
import seo.dale.http.client.template.RestTemplateBuilder;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Interact with JSONPlaceholder(http://jsonplaceholder.typicode.com)
 */
@RunWith(SpringRunner.class)
public class HttpClientSpringTest {

	@Autowired
	private HttpClient httpClient;

	@Test
	public void testGet() {
		Post detail = httpClient.get("/posts/1", Post.class);
		System.out.println("### testGet: " + detail);
		assertThat(detail.getId()).isEqualTo(1);
	}

	@Test
	public void testPost() {
		Post toCreate = new Post();
		toCreate.setTitle("foo");
		toCreate.setBody("bar");
		toCreate.setUserId(1);

		Post created = httpClient.post("/posts", Post.class, toCreate);
		System.out.println("### testPost: " + created);
		assertThat(created.getId()).isEqualTo(101);
		assertThat(created.getTitle()).isEqualTo("foo");
		assertThat(created.getBody()).isEqualTo("bar");
		assertThat(created.getUserId()).isEqualTo(1);
	}

	@Test
	public void testPut() {
		Post toModify = new Post();
		toModify.setId(1);
		toModify.setTitle("foo");
		toModify.setBody("bar");
		toModify.setUserId(1);

		Post modified = httpClient.put("/posts/1", Post.class, toModify);
		System.out.println("### testPut: " + modified);
		assertThat(modified.getId()).isEqualTo(1);
		assertThat(modified.getTitle()).isEqualTo("foo");
		assertThat(modified.getBody()).isEqualTo("bar");
		assertThat(modified.getUserId()).isEqualTo(1);
	}

	@Test
	public void testDelete() {
		Post deleted = httpClient.delete("/posts/1", Post.class);
		System.out.println("### testDelete: " + deleted);
		assertThat(deleted.getId()).isNull();
		assertThat(deleted.getTitle()).isNull();
		assertThat(deleted.getBody()).isNull();
		assertThat(deleted.getUserId()).isNull();
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
			HttpClientLoggerConfig config = HttpClientLoggerConfig
					.custom()
					.build();
			RestTemplate restTemplate = new RestTemplateBuilder()
					.interceptors(Collections.singletonList(new LogInterceptor(config)))
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