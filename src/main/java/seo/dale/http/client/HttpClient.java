package seo.dale.http.client;

public interface HttpClient {

	<T> T get(String path, String... query);

	<T> T post(String path, Object body, String... query);

	<T> T put(String path, Object body, String... query);

	<T> T delete(String path, String... query);

}
