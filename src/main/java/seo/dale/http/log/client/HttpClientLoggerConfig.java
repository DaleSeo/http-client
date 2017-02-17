package seo.dale.http.log.client;

/**
 * HttpClient 로거 설정
 */
public class HttpClientLoggerConfig {

	public enum LogStyle {CURL, SINGLE_LINE}

	/** 로거 이름 */
	private final String loggerName;

	/** 로그 레벨 */
	private final String logLevel;

	/** 로그 스타일 */
	private final LogStyle logStyle;

	/** 바디 로그 최대 길이 */
	private final int maxPayloadLength;

	/** 바디 줄바꿈 출력 여부 */
	private final boolean payloadPretty;

	/** 로그 메세지 접두어 */
	private final String messagePrefix;

	/** 연동 성공 시 응답 전문 출력 생략 여부 */
	private final boolean skippableOnSuccess;

	private HttpClientLoggerConfig(Builder builder) {
		loggerName = builder.loggerName;
		logLevel = builder.logLevel;
		logStyle = builder.logStyle;
		maxPayloadLength = builder.maxPayloadLength;
		payloadPretty = builder.payloadPretty;
		messagePrefix = builder.messagePrefix;
		skippableOnSuccess = builder.skippableOnSuccess;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public LogStyle getLogStyle() {
		return logStyle;
	}

	public String getMessagePrefix() {
		return messagePrefix;
	}

	public int getMaxPayloadLength() {
		return maxPayloadLength;
	}

	public boolean isPayloadPretty() {
		return payloadPretty;
	}

	public boolean isSkippableOnSuccess() {
		return skippableOnSuccess;
	}

	public static Builder custom() {
		return new Builder();
	}

	public static class Builder {

		private String loggerName = "seo.dale.http";

		private String logLevel = "DEBUG";

		public LogStyle logStyle = LogStyle.CURL;

		private int maxPayloadLength = 1000;

		private boolean payloadPretty = true;

		private String messagePrefix = null;

		private boolean skippableOnSuccess = false;

		public Builder loggerName(String loggerName) {
			this.loggerName = loggerName;
			return this;
		}

		public Builder logLevel(String logLevel) {
			this.logLevel = logLevel;
			return this;
		}

		public Builder logStyle(LogStyle logStyle) {
			this.logStyle = logStyle;
			return this;
		}

		public Builder maxPayloadLength(int maxPayloadLength) {
			this.maxPayloadLength = maxPayloadLength;
			return this;
		}

		public Builder payloadPretty(boolean payloadPretty) {
			this.payloadPretty = payloadPretty;
			return this;
		}

		public Builder messagePrefix(String messagePrefix) {
			this.messagePrefix = messagePrefix;
			return this;
		}

		public Builder skippableOnSuccess(boolean skippableOnSuccess) {
			this.skippableOnSuccess = skippableOnSuccess;
			return this;
		}

		public HttpClientLoggerConfig build() {
			return new HttpClientLoggerConfig(this);
		}

	}

}