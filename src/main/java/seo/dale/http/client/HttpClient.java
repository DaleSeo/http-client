package seo.dale.http.client;

import seo.dale.http.client.model.HttpClientMethod;
import seo.dale.http.client.model.HttpClientRequest;
import seo.dale.http.client.model.HttpClientResponse;
import seo.dale.http.client.model.HttpClientUrl;

public interface HttpClient {

	<T> T get(String path, Class<T> responseType, Object... pathVars);

	<T> T post(String path, Class<T> responseType, Object body, Object... pathVars);

	<T> T put(String path, Class<T> responseType, Object body, Object... pathVars);

	<T> T delete(String path, Class<T> responseType, Object... pathVars);

	<O> HttpClientResponse<O> exchange(HttpClientMethod method, HttpClientUrl url, HttpClientRequest request, Class<O> responseType, Object... vars);

}
