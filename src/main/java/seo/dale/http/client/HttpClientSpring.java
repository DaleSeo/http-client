package seo.dale.http.client;

import org.springframework.web.client.RestTemplate;

public class HttpClientSpring implements HttpClient {

	private RestTemplate restTemplate;

	public HttpClientSpring(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public <T> T get(String path, String... query) {
		return null;
	}

	@Override
	public <T> T post(String path, Object body, String... query) {
		return null;
	}

	@Override
	public <T> T put(String path, Object body, String... query) {
		return null;
	}

	@Override
	public <T> T delete(String path, String... query) {
		return null;
	}

}
