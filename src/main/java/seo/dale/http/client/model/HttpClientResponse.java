package seo.dale.http.client.model;

import java.util.Map;

/**
 * 응답 모델
 */
public class HttpClientResponse<O> extends HttpClientModel {

	/** 상태 정보 **/
	private Status status;

	/** 응답 헤더 */
	private Map<String, String> headers;

	/** 응답 바디 */
	private O body;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public O getBody() {
		return body;
	}

	public void setBody(O body) {
		this.body = body;
	}

	public static class Status {

		private int code;

		private String text;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return code + " " + text;
		}

	}

}
