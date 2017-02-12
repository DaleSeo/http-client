package seo.dale.http.client.template;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;
import seo.dale.http.client.error.HttpClientErrorHandler;
import seo.dale.http.client.intercept.LoggingInterceptor;

import java.util.List;

/**
 * RestTemplate 빌더
 * <li>복잡한 RestTemplate 설정을 빌더로 편리하고 안전하게</li>
 * <li>미설정 항목들은 관행에 따라 디폴트값으로 설정해줌 (Convention over Configuration)</li>
 */
public class RestTemplateBuilder {

	private static final ClientHttpRequestFactory DEFAULT_REQUEST_FACTORY = DefaultRestTemplateComponents.requestFactory();
	private static final UriTemplateHandler DEFAULT_URI_TEMPLATE_HANDLER = DefaultRestTemplateComponents.uriTemplateHandler();
	private static final List<ClientHttpRequestInterceptor> DEFAULT_INTERCEPTORS = DefaultRestTemplateComponents.interceptors();
	private static final List<HttpMessageConverter<?>> DEFAULT_MESSAGE_CONVERTERS = DefaultRestTemplateComponents.messageConverters();
	private static final ResponseErrorHandler DEFAULT_ERROR_HANDLER = DefaultRestTemplateComponents.errorHandler();

	private ClientHttpRequestFactory requestFactory = DEFAULT_REQUEST_FACTORY;
	private UriTemplateHandler uriTemplateHandler= DEFAULT_URI_TEMPLATE_HANDLER;
	private List<ClientHttpRequestInterceptor> interceptors = DEFAULT_INTERCEPTORS;
	private List<HttpMessageConverter<?>> messageConverters = DEFAULT_MESSAGE_CONVERTERS;
	private ResponseErrorHandler errorHandler = DEFAULT_ERROR_HANDLER;

	/**
	 * RestTemplate 객체를 생성한다.
	 */
	public RestTemplate build() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.setRequestFactory(requestFactory);
		restTemplate.setUriTemplateHandler(uriTemplateHandler);
		restTemplate.setInterceptors(interceptors);
		restTemplate.setMessageConverters(messageConverters);
		restTemplate.setErrorHandler(errorHandler);

		makeRequestFactoryContentKeeping(restTemplate);

		return restTemplate;
	}

	/**
	 * 요청/응답 전문 보존을 위해 BufferingClientHttpRequestFactory로 래핑해준다.
	 */
	private void makeRequestFactoryContentKeeping(RestTemplate restTemplate) {
		// Logging Interceptor 사용 시, BufferingClientHttpRequestFactory로 변환
		if (!CollectionUtils.isEmpty(interceptors)) {
			restTemplate.setInterceptors(interceptors);
			interceptors.stream()
					.filter(interceptor -> interceptor instanceof LoggingInterceptor)
					.forEach(interceptor -> {
						if (!(requestFactory instanceof BufferingClientHttpRequestFactory)) {
							requestFactory = new BufferingClientHttpRequestFactory(requestFactory);
						}
					});
		}
	}

	/**
	 * 세팅된 errorHandler가 없으면 SacClientErrorHandler를 세팅해준다.
	 */
	private void setDefaultErrorHandler() {
		errorHandler = new HttpClientErrorHandler();
	}

	public RestTemplateBuilder requestFactory(ClientHttpRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
		return this;
	}

	public RestTemplateBuilder uriTemplateHandler(UriTemplateHandler uriTemplateHandler) {
		this.uriTemplateHandler = uriTemplateHandler;
		return this;
	}

	public RestTemplateBuilder interceptors(List<ClientHttpRequestInterceptor> interceptors) {
		this.interceptors = interceptors;
		return this;
	}

	public RestTemplateBuilder messageConverters(List<HttpMessageConverter<?>> messageConverters) {
		this.messageConverters = messageConverters;
		return this;
	}

	public RestTemplateBuilder errorHandler(ResponseErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
		return this;
	}

}
