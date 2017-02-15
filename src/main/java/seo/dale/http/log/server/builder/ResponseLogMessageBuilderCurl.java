package seo.dale.http.log.server.builder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import seo.dale.http.log.common.LogConstants;
import seo.dale.http.log.server.extractor.ResponseLogInfoExtractor;

/**
 * 응답 로그 메세지 빌더 (Curl 스타일)
 */
public class ResponseLogMessageBuilderCurl implements ResponseLogMessageBuilder {

	/** 헤더 로깅 여부 */
	private boolean shouldIncludeHeaders;

	/** 바디 로깅 최대 길이 */
	private int maxBodyLength;

	public ResponseLogMessageBuilderCurl(boolean shouldIncludeHeaders, int maxBodyLength) {
		this.shouldIncludeHeaders = shouldIncludeHeaders;
		this.maxBodyLength = maxBodyLength;
	}

	/**
	 * RequestLogInfoExtractor를 이용하여 로그 메세지를 만든다
	 */
	@Override
	public String build(ResponseLogInfoExtractor responseInfoExtractor) {
		StringBuilder builder = new StringBuilder();

		// Prefix
		builder.append("[[").append(LogConstants.SERVER_RESPONSE_LOG_PREFIX).append("]]\n");

		String protocol = responseInfoExtractor.extractProtocol();
		HttpStatus status = responseInfoExtractor.extractStatus();

		// 필수 옵션
		builder.append(protocol).append(" ").append(status.toString()).append(" ").append(status.getReasonPhrase());

		// 헤더 옵션
		if (shouldIncludeHeaders) {
			builder.append("\n");
			HttpHeaders headers = responseInfoExtractor.extractHeaders();
			for (String name : headers.keySet()) {
				String value = headers.getFirst(name);
				builder.append("> ").append(name).append(": ").append(value).append("\n");
			}
			if (builder.length() > 0) {
				builder.setLength(builder.length() - 1);
			}
		}

		// 바디 옵션
		if (maxBodyLength > 0) {
			String body = responseInfoExtractor.extractBody(maxBodyLength);
			if (StringUtils.isNotBlank(body)) {
				builder.append("\n").append(body);
			}
		}

		return builder.toString();
	}

}
