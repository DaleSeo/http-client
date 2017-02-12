package seo.dale.http.client;

public interface HttpClient {

	<T> T get(String path, Class<T> responseType, String... pathVars);

	<T> T post(String path, Class<T> responseType, Object body, String... pathVars);

	<T> T put(String path, Class<T> responseType, Object body, String... pathVars);

	<T> T delete(String path, Class<T> responseType, String... pathVars);

}
