package seo.dale.http.log.server.builder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import seo.dale.http.log.common.LogConstants;
import seo.dale.http.log.server.extractor.ResponseLogInfoExtractor;

/**
 * 응답 로그 메세지 빌더 (한 줄 스타일)
 */
public class ResponseLogMessageBuilderSingleLine implements ResponseLogMessageBuilder {

	/** 헤더 로깅 여부 */
	private boolean shouldIncludeHeaders;

	/** 바디 로깅 최대 길이 */
	private int maxBodyLength;

	public ResponseLogMessageBuilderSingleLine(boolean shouldIncludeHeaders, int maxBodyLength) {
		this.shouldIncludeHeaders = shouldIncludeHeaders;
		this.maxBodyLength = maxBodyLength;
	}

	@Override
	public String build(ResponseLogInfoExtractor responseInfoExtractor) {
		StringBuilder builder = new StringBuilder();

		// Prefix
		builder.append(LogConstants.SERVER_RESPONSE_LOG_PREFIX).append(" >> ");

		String protocol = responseInfoExtractor.extractProtocol();
		HttpStatus status = responseInfoExtractor.extractStatus();

		// 필수 옵션
		builder.append(protocol).append(" ").append(status.toString()).append(" ").append(status.getReasonPhrase());

		// 헤더 옵션
		if (shouldIncludeHeaders) {
			HttpHeaders headers = responseInfoExtractor.extractHeaders();
			if (!headers.isEmpty()) {
				builder.append(" || HEADERS > ").append(headers);
			}
		}

		// 바디 옵션
		if (maxBodyLength > 0) {
			String body = responseInfoExtractor.extractBody(maxBodyLength);
			if (StringUtils.isNotBlank(body)) {
				builder.append(" || BODY > ").append(body);
			}
		}

		return builder.toString();
	}
}
