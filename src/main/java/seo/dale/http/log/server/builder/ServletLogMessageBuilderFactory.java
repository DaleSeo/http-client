package seo.dale.http.log.server.builder;

/**
 * 서블릿 로그 메세지 빌더 팩토리
 */
public class ServletLogMessageBuilderFactory {

	private boolean shouldIncludeHeaders;

	private int maxPayloadLength;

	public ServletLogMessageBuilderFactory(boolean shouldIncludeHeaders, int maxPayloadLength) {
		this.shouldIncludeHeaders = shouldIncludeHeaders;
		this.maxPayloadLength = maxPayloadLength;
	}

	/**
	 * 로깅 스타일에 따라 적합한 요청 로그 메세지 빌더를 생성한다.
	 */
	public RequestLogMessageBuilder createForRequest(ServerLogStyle loggingStyle) {
		switch (loggingStyle) {
			case SINGLE_LINE:
				return new RequestLogMessageBuilderSingleLine(shouldIncludeHeaders, maxPayloadLength);
			default:
				return new RequestLogMessageBuilderCurl(shouldIncludeHeaders, maxPayloadLength);
		}
	}

	/**
	 * 로깅 스타일에 따라 적합한 응답 로그 메세지 빌더를 생성한다.
	 */
	public ResponseLogMessageBuilder createForResponse(ServerLogStyle loggingStyle) {
		switch (loggingStyle) {
			case SINGLE_LINE:
				return new ResponseLogMessageBuilderSingleLine(shouldIncludeHeaders, maxPayloadLength);
			default:
				return new ResponseLogMessageBuilderCurl(shouldIncludeHeaders, maxPayloadLength);
		}
	}

}
