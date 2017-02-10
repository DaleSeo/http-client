package seo.dale.http.client.invoke;

import seo.dale.http.client.model.HttpClientMethod;
import seo.dale.http.client.model.HttpClientRequest;
import seo.dale.http.client.model.HttpClientResponse;
import seo.dale.http.client.model.HttpClientUrl;

/**
 * HTTP API 호출기 인터페이스
 */
public interface HttpClientInvoker {

	/**
	 * HTTP API를 호출하는 템플릿 메소드
	 * @param <I> 요청 타입
	 * @param <O> 응답 타입
	 * @param method Method 모델
	 * @param url Url 모델
	 * @param request 요청 모델
	 * @return 응답 모델
	 */
	<I, O> HttpClientResponse<O> invoke(HttpClientMethod method, HttpClientUrl url, HttpClientRequest<I> request, Class<O> responseType, String... vars);

}
