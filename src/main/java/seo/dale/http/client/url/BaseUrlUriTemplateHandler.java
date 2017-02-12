package seo.dale.http.client.url;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;
import seo.dale.http.client.resolve.BaseUrlResolver;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Base URL을 삽입 용 커스텀 UriTemplateHandler
 */
public class BaseUrlUriTemplateHandler implements UriTemplateHandler {

	private BaseUrlResolver urlResolver;

	public BaseUrlUriTemplateHandler(BaseUrlResolver urlResolver) {
		this.urlResolver = urlResolver;
	}

	@Override
	public URI expand(String uriTemplate, Map<String, ?> uriVariables) {
		UriComponentsBuilder uriComponentsBuilder = initUriComponentsBuilder(uriTemplate);
		UriComponents uriComponents = uriComponentsBuilder.buildAndExpand(uriVariables);
		return insertBaseUrl(uriComponents);
	}

	@Override
	public URI expand(String uriTemplate, Object... uriVariableValues) {
		UriComponentsBuilder uriComponentsBuilder = initUriComponentsBuilder(uriTemplate);
		UriComponents uriComponents = uriComponentsBuilder.buildAndExpand(uriVariableValues);
		return insertBaseUrl(uriComponents);
	}

	/**
	 * Sub Url 이 들어올 수도 있고, Full Url 이 들어올 수 도 있음
	 */
	protected UriComponentsBuilder initUriComponentsBuilder(String uriTemplate) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uriTemplate);
		return builder;
	}

	protected URI insertBaseUrl(UriComponents uriComponents) {
		String baseUrl = urlResolver.resolveBaseUrl();

		if (baseUrl == null || uriComponents.getHost() != null) {
			return uriComponents.toUri();
		}
		String url = baseUrl + uriComponents.toUriString();
		try {
			return new URI(url);
		}
		catch (URISyntaxException ex) {
			throw new IllegalArgumentException("Invalid URL after inserting base URL: " + url, ex);
		}
	}

}
