package seo.dale.http.client.log.model;

import org.springframework.http.HttpStatus;

/**
 * 클라이언트 응답 로깅 정보
 */
public class ClientResponseLogInfo extends ClientLogInfo {

	private HttpStatus httpStatus;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
