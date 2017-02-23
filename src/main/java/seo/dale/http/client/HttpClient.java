package seo.dale.http.client;

import seo.dale.http.client.model.HttpClientMethod;
import seo.dale.http.client.model.HttpClientRequest;
import seo.dale.http.client.model.HttpClientResponse;
import seo.dale.http.client.model.HttpClientUrl;

public interface HttpClient {

	<T> T get(String path, Class<T> responseType, String... pathVars);

	<T> T post(String path, Class<T> responseType, Object body, String... pathVars);

	<T> T put(String path, Class<T> responseType, Object body, String... pathVars);

	<T> T delete(String path, Class<T> responseType, String... pathVars);

	<O> HttpClientResponse<O> exchange(HttpClientMethod method, HttpClientUrl url, HttpClientRequest request, Class<O> responseType, Object body, Object... vars);

}
