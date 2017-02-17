package seo.dale.http.log.server.builder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import seo.dale.http.log.common.LogConstants;
import seo.dale.http.log.server.extractor.RequestLogInfoExtractor;

/**
 * 요청 로그 메세지 빌더 (Curl 스타일)
 */
public class RequestLogMessageBuilderCurl implements RequestLogMessageBuilder {

	/** 헤더 로깅 여부 */
	private boolean shouldIncludeHeaders;

	/** 바디 로깅 최대 길이 */
	private int maxBodyLength;

	public RequestLogMessageBuilderCurl(boolean shouldIncludeHeaders, int maxBodyLength) {
		this.shouldIncludeHeaders = shouldIncludeHeaders;
		this.maxBodyLength = maxBodyLength;
	}

	/**
	 * RequestLogInfoExtractor를 이용하여 로그 메세지를 만든다
	 */
	@Override
	public String build(RequestLogInfoExtractor requestInfoExtractor) {
		StringBuilder builder = new StringBuilder();

		// Prefix
			builder.append("[[").append(LogConstants.SERVER_REQUEST_LOG_PREFIX).append("]]\n");

		String method = requestInfoExtractor.extractMethod();
		String url = requestInfoExtractor.extractUrl();
		String queryString = requestInfoExtractor.extractQueryString();
		String protocol = requestInfoExtractor.extractProtocol();

		// 필수 옵션
		builder.append(method).append(" ").append(url);

		if (queryString != null) {
			builder.append("?").append(queryString);
		}

		builder.append(" ").append(protocol);

		// 헤더 옵션
		if (shouldIncludeHeaders) {
			builder.append("\n");
			HttpHeaders headers = requestInfoExtractor.extractHeaders();
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
			String body = requestInfoExtractor.extractBody(maxBodyLength);
			if (StringUtils.isNotBlank(body)) {
				builder.append("\n").append(body);
			}
		}

		return builder.toString();
	}

}
