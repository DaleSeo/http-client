package seo.dale.http.client.invoke;

import seo.dale.http.client.model.HttpClientMethod;
import seo.dale.http.client.model.HttpClientRequest;
import seo.dale.http.client.model.HttpClientResponse;
import seo.dale.http.client.model.HttpClientUrl;

/**
 * TODO : Apache HttpClient를 이용하여 HTTP API 호출기 구현
 */
public class HttpClientInvokerApache implements HttpClientInvoker {

	@Override
	public <I, O> HttpClientResponse<O> invoke(HttpClientMethod method, HttpClientUrl url, HttpClientRequest<I> request, Class<O> responseType, Object... vars) {
		return null;
	}

}
