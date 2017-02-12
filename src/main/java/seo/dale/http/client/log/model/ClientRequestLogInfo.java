package seo.dale.http.client.log.model;

import org.springframework.http.HttpMethod;

import java.net.URI;

/**
 * 클라이언트 요청 로깅 정보
 */
public class ClientRequestLogInfo extends ClientLogInfo {

	private HttpMethod method;

	private URI uri;

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

}
