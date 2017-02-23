package seo.dale.http.client.url;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import seo.dale.http.client.common.HttpClientConstants;
import seo.dale.http.client.model.HttpClientUrl;

/**
 * Url 문자열 빌더
 */
public class UrlStringBuilder {

	private final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

	/**
	 * URL 객체와 Method 객체를 이용하여 URL 스트링을 만든다.
	 * Url 객체에 host 포함 시, Full(Base + Sub) URL 문자열을 반환하고, Url 객체에 host 미포함 시에는 Sub URL문자열만 반환한다.
	 * @return URL 문자열
	 */
	public String buildUrl(HttpClientUrl url) {
		// Url 객체가 host 포함 시, Full Url 만들어서 삽입
		if (StringUtils.isNotBlank(url.getHost())) {
			insertBaseUrl(url.getScheme(), url.getHost(), url.getPort());
			uriComponentsBuilder.path(url.getPath());
			return uriComponentsBuilder.build().toString();
		}

		return UriComponentsBuilder.fromUriString(url.getPath()).build().toString();
	}

	private void insertBaseUrl(HttpClientUrl.Scheme scheme, String host, int port) {
		uriComponentsBuilder.scheme(scheme.toString()).host(host);
		if (port != HttpClientConstants.DEFAULT_PORT) {
			uriComponentsBuilder.port(port);
		}
	}

}
