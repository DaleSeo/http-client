package seo.dale.http.client.log.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpHeaders;

/**
 * 클라이언트 로깅 정보
 * : ClientRequestLoggingInfo와 ClientResponseLoggingInfo의 공통 속성을 정의힌다
 */
public class ClientLogInfo {

	private HttpHeaders httpHeaders;

	private String payload;

	public boolean hasHttpHeaders() {
		return httpHeaders != null && !httpHeaders.isEmpty();
	}

	public boolean hasPayload() {
		return StringUtils.isNotEmpty(payload);
	}

	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}

	public void setHttpHeaders(HttpHeaders httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
