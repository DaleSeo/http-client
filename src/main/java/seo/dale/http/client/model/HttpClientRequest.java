package seo.dale.http.client.model;

import java.util.List;
import java.util.Map;

/**
 * 요청 모델
 */
public class HttpClientRequest<I> extends HttpClientModel {

	/**
	 * 요청 헤더
	 */
	private Map<String, List<String>> headers;

	/**
	 * 요청 바디
	 */
	private I body;

	public HttpClientRequest() {
		this.headers = headers;
	}

	/**
	 * GET 방식 용 생성자 : 헤더만 받음
	 */
	public HttpClientRequest(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public HttpClientRequest(I body){
		this.body = body;
	}

	/**
	 * POST 방식 용 생성자 : 헤더와 바디를 모두 받음
	 */
	public HttpClientRequest(Map<String, List<String>> headers, I body) {
		this.headers = headers;
		this.body = body;
	}

	public I getBody() {
		return body;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

}
