package seo.dale.http.log.server;

/**
 * HTTP 서버 로거 설정
 */
public class HttpServerLoggerConfig {

	/** 로거 이름 */
	private String loggerName;

	/** 로그 레벨 */
	private String logLevel;

	/** 요청 정보 로깅 여부 */
	private boolean requestLoggingEnabled;

	/** 응답 정보 로깅 여부 */
	private boolean responseLoggingEnabled;

	/** 헤더 로깅 여부 */
	private boolean shouldIncludeHeaders;

	/** 바디 로깅 최대 길이 */
	private int maxPayloadLength;

	/** 로깅 스타일 */
	private HttpServerLogStyle style = HttpServerLogStyle.CURL;

	public HttpServerLoggerConfig(Builder builder) {
		loggerName = builder.loggerName;
		logLevel = builder.logLevel;
		requestLoggingEnabled = builder.requestLoggingEnabled;
		responseLoggingEnabled = builder.responseLoggingEnabled;
		shouldIncludeHeaders = builder.shouldIncludeHeaders;
		maxPayloadLength = builder.maxPayloadLength;
		style = builder.style;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public boolean isRequestLoggingEnabled() {
		return requestLoggingEnabled;
	}

	public boolean isResponseLoggingEnabled() {
		return responseLoggingEnabled;
	}

	public boolean isShouldIncludeHeaders() {
		return shouldIncludeHeaders;
	}

	public int getMaxPayloadLength() {
		return maxPayloadLength;
	}

	public HttpServerLogStyle getStyle() {
		return style;
	}

	public static Builder custom() {
		return new Builder();
	}

	public static class Builder {

		private String loggerName;

		private String logLevel = "debug";

		private boolean requestLoggingEnabled = true;

		private boolean responseLoggingEnabled = false;

		private boolean shouldIncludeHeaders = false;

		private int maxPayloadLength = 50;

		private HttpServerLogStyle style = HttpServerLogStyle.CURL;

		public Builder loggerName(String loggerName) {
			this.loggerName = loggerName;
			return this;
		}

		public Builder logLevel(String logLevel) {
			this.logLevel = logLevel;
			return this;
		}

		public Builder requestLoggingEnabled(boolean requestLoggingEnabled) {
			this.requestLoggingEnabled = requestLoggingEnabled;
			return this;
		}

		public Builder responseLoggingEnabled(boolean responseLoggingEnabled) {
			this.responseLoggingEnabled = responseLoggingEnabled;
			return this;
		}

		public Builder shouldIncludeHeaders(boolean shouldIncludeHeaders) {
			this.shouldIncludeHeaders = shouldIncludeHeaders;
			return this;
		}

		public Builder maxPayloadLength(int maxPayloadLength) {
			this.maxPayloadLength = maxPayloadLength;
			return this;
		}

		public Builder style(HttpServerLogStyle style) {
			this.style = style;
			return this;
		}

		public HttpServerLoggerConfig build() {
			return new HttpServerLoggerConfig(this);
		}

	}


}
