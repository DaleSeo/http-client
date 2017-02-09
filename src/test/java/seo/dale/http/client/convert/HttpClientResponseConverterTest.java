package seo.dale.http.client.convert;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import seo.dale.http.client.model.HttpClientResponse;
import seo.dale.http.client.model.Post;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpClientResponseConverterTest {

	@Test
	public void test() {
		Post body = new Post();
		body.setId(1);
		body.setTitle("foo");
		body.setBody("bar");
		body.setUserId(1);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentLength(456);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		httpHeaders.set("guid", "85f1f03");

		ResponseEntity<Post> responseEntity = new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);

		HttpClientResponseConverter<Post> converter = new HttpClientResponseConverter<>();
		HttpClientResponse<Post> response = converter.convert(responseEntity);

		assertThat(response.getStatus().toString()).isEqualTo("200 OK");
		assertThat(response.getBody()).isEqualTo(body);

		Map<String, List<String>> headers = response.getHeaders();

		assertThat(headers.get("Content-Length").get(0)).isEqualTo("456");
		assertThat(headers.get("Content-Type").get(0)).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
		assertThat(headers.get("guid").get(0)).isEqualTo("85f1f03");
	}

}