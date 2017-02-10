package seo.dale.http.client.invoke;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import seo.dale.http.client.convert.HttpClientRequestConverter;
import seo.dale.http.client.convert.HttpClientResponseConverter;
import seo.dale.http.client.url.UrlStringBuilder;
import seo.dale.http.client.model.HttpClientMethod;
import seo.dale.http.client.model.HttpClientRequest;
import seo.dale.http.client.model.HttpClientResponse;
import seo.dale.http.client.model.HttpClientUrl;

/**
 * Spring RestTemplate를 이용하여 HTTP API 호출기 구현
 */
public class HttpClientInvokerSpring implements HttpClientInvoker {

	private RestTemplate restTemplate;

	public HttpClientInvokerSpring(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public <I, O> HttpClientResponse<O> invoke(HttpClientMethod method, HttpClientUrl url, HttpClientRequest<I> request, Class<O> responseType, String... vars) {
		// 1) Build the url string
		String urlStr = new UrlStringBuilder().buildUrl(url);
		// 2) Convert SacClientRequest to RequestEntity
		HttpEntity<I> requestEntity = new HttpClientRequestConverter<I>().convert(request);
		// 3) Invoke the SAC REST API using Spring RestTemplate
		ResponseEntity<O> responseEntity = restTemplate.exchange(urlStr, HttpMethod.valueOf(method.name()), requestEntity, responseType, vars);
		// 4) Convert ResponseEntity to SacClientResponse
		HttpClientResponse<O> response = new HttpClientResponseConverter<O>().convert(responseEntity);
		return response;
	}

}
