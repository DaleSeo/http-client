package seo.dale.http.client.convert;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import seo.dale.http.client.model.HttpClientRequest;

import java.util.List;
import java.util.Map;

/**
 * 라이브러리의 HttpClient 요청 모델을 스프링의 HttpHeaders로 변환한다.
 */
public class HttpClientRequestConverter<I> {

	public HttpEntity<I> convert(HttpClientRequest<I> request) {
		Map<String, List<String>> headers = request.getHeaders();
		I body = request.getBody();

		HttpHeaders httpHeaders = new HttpHeaders();
		if (headers != null) {
			httpHeaders.putAll(headers);
		}

		if (body != null) {
			return new HttpEntity<>(body, httpHeaders);
		} else {
			return new HttpEntity<>(httpHeaders);
		}
	}

}
