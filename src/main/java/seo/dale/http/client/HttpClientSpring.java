package seo.dale.http.client;

import org.springframework.web.client.RestTemplate;
import seo.dale.http.client.invoke.HttpClientInvoker;
import seo.dale.http.client.invoke.HttpClientInvokerSpring;
import seo.dale.http.client.model.HttpClientMethod;
import seo.dale.http.client.model.HttpClientRequest;
import seo.dale.http.client.model.HttpClientResponse;
import seo.dale.http.client.model.HttpClientUrl;
import seo.dale.http.client.resolve.BaseUrlResolver;
import seo.dale.http.client.resolve.HeaderResolver;
import seo.dale.http.client.template.RestTemplateConfigurer;

/**
 * HttpClietn
 */
public class HttpClientSpring implements HttpClient {

	private HttpClientInvoker invoker;

	public HttpClientSpring(RestTemplate restTemplate) {
		invoker = new HttpClientInvokerSpring(restTemplate);
	}

	public HttpClientSpring(RestTemplate restTemplate, BaseUrlResolver baseUrlResolver, HeaderResolver headerResolver) {
		RestTemplateConfigurer configurer = new RestTemplateConfigurer(restTemplate);
		configurer.configure(baseUrlResolver);
		configurer.configure(headerResolver);
		invoker = new HttpClientInvokerSpring(restTemplate);
	}

	@Override
	public <T> T get(String path, Class<T> responseType, Object... pathVars) {
		return exchange(HttpClientMethod.GET, path, responseType, null, pathVars).getBody();
	}

	@Override
	public <T> T post(String path, Class<T> responseType, Object body, Object... pathVars) {
		return exchange(HttpClientMethod.POST, path, responseType, body, pathVars).getBody();
	}

	@Override
	public <T> T put(String path, Class<T> responseType, Object body, Object... pathVars) {
		return exchange(HttpClientMethod.PUT, path, responseType, body, pathVars).getBody();
	}

	@Override
	public <T> T delete(String path, Class<T> responseType, Object... pathVars) {
		return exchange(HttpClientMethod.DELETE, path, responseType, pathVars).getBody();
	}

	private <O> HttpClientResponse<O> exchange(HttpClientMethod method, String path, Class<O> responseType, Object body, Object... vars) {
		HttpClientUrl url = new HttpClientUrl.Builder().path(path).build();
		HttpClientRequest<Object> request = new HttpClientRequest<>(body);
		return exchange(method, url, request, responseType, vars);
	}

	@Override
	public <O> HttpClientResponse<O> exchange(HttpClientMethod method, HttpClientUrl url, HttpClientRequest request, Class<O> responseType, Object body, Object... vars) {
		return invoker.invoke(method, url, request, responseType, vars);
	}

}
