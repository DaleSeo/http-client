package seo.dale.http.client.template;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;
import seo.dale.http.client.intercept.HeaderInterceptor;
import seo.dale.http.client.resolve.BaseUrlResolver;
import seo.dale.http.client.resolve.HeaderResolver;
import seo.dale.http.client.url.BaseUrlUriTemplateHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestTemplateConfigurer {

	private RestTemplate restTemplate;

	public RestTemplateConfigurer(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * baseUrlProvider로 BaseUrlUriTemplateHandler를 만들어 restTemplate에 설정해준다.
	 */
	public void configure(BaseUrlResolver baseUrlProvider) {
		if (baseUrlProvider == null) {
			return;
		}
		UriTemplateHandler uriTemplateHandler = new BaseUrlUriTemplateHandler(baseUrlProvider);
		restTemplate.setUriTemplateHandler(uriTemplateHandler);
	}

	/**
	 * headerResolverr로 HeaderSupplementInterceptor을 만들어 restTemplate에 설정해준다.
	 */
	public void configure(HeaderResolver headerResolver) {
		if (headerResolver == null) {
			return;
		}

		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		HeaderInterceptor headerSupplementInterceptor = new HeaderInterceptor();

		if (interceptors == null) {
			interceptors = Collections.singletonList(headerSupplementInterceptor); // 단독 인터셉터
		} else {
			interceptors = new ArrayList<>(interceptors); // in case of an immutable list
			interceptors.add(0, headerSupplementInterceptor); // 맨 앞 인터셉터
		}

		restTemplate.setInterceptors(interceptors);
	}

}