package seo.dale.http.client.template;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;
import seo.dale.http.client.error.HttpClientErrorHandler;

import java.util.ArrayList;
import java.util.List;

public class DefaultRestTemplateComponents {

	public static int DEFAULT_CONNECT_TIMEOUT = 2000;
	public static int DEFAULT_READ_TIMEOUT = 10000;

	public static RestTemplate DEFAULT_REST_TEMPLATE = new RestTemplate();

	public static ClientHttpRequestFactory requestFactory() {
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
		clientHttpRequestFactory.setReadTimeout(DEFAULT_READ_TIMEOUT);
		return clientHttpRequestFactory;
	}

	public static UriTemplateHandler uriTemplateHandler() {
		return DEFAULT_REST_TEMPLATE.getUriTemplateHandler();
	}

	public static List<ClientHttpRequestInterceptor> interceptors() {
		return DEFAULT_REST_TEMPLATE.getInterceptors();
	}

	public static List<HttpMessageConverter<?>> messageConverters() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>(2);

		// plain/text
		StringHttpMessageConverter stringMessageConverter = new StringHttpMessageConverter();
		stringMessageConverter.setWriteAcceptCharset(false);
		messageConverters.add(stringMessageConverter);

		// application/json
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonMessageConverter.setPrettyPrint(true);
		messageConverters.add(jsonMessageConverter);

		return messageConverters;
	}

	public static ResponseErrorHandler errorHandler() {
		return new HttpClientErrorHandler();
	}

}
