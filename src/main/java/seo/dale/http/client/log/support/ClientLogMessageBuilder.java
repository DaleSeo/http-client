package seo.dale.http.client.log.support;

import org.springframework.http.HttpStatus;
import seo.dale.http.client.log.model.ClientRequestLogInfo;
import seo.dale.http.client.log.model.ClientResponseLogInfo;
import seo.dale.http.client.log.common.LogConstants;

/**
 * 클라이언트 요청 로그 메세지 빌더
 */
public class ClientLogMessageBuilder {

	private String messagePrefix;
	private boolean skippableOnSuccess;

	public ClientLogMessageBuilder(String messagePrefix, boolean skippableOnSuccess) {
		this.messagePrefix = messagePrefix;
		this.skippableOnSuccess = skippableOnSuccess;
	}

	/**
	 * 클라이언트 요청 로깅을 위한 메세지를 만든다.
	 */
	public String buildRequestLogMessage(ClientRequestLogInfo loggingInfo) {
		StringBuilder builder = new StringBuilder(messagePrefix + "_" + LogConstants.REQUEST_LOG_PREFIX);
		builder.append("\t").append(loggingInfo.getMethod());
		builder.append("\t").append(loggingInfo.getUri());

		if (loggingInfo.hasHttpHeaders()) {
			builder.append("\tHEADER").append(loggingInfo.getHttpHeaders());
		}

		if (loggingInfo.hasPayload()) {
			builder.append("\tBODY").append(loggingInfo.getPayload());
		}

		return builder.toString();
	}

	/**
	 * 클라이언트 응답 로깅을 위한 메세지를 만든다.
	 */
	public String buildResponseLogMessage(ClientResponseLogInfo loggingInfo) {
		HttpStatus httpStatus = loggingInfo.getHttpStatus();

		StringBuilder builder = new StringBuilder(messagePrefix + "_" + LogConstants.RESPONSE_LOG_PREFIX);
		builder.append("\t").append(loggingInfo.getHttpStatus());

		if (loggingInfo.hasHttpHeaders()) {
			builder.append("\tHEADER").append(loggingInfo.getHttpHeaders());
		}

		boolean failed = httpStatus.is5xxServerError();
		if (!skippableOnSuccess || failed) {
			if (loggingInfo.hasPayload()) {
				builder.append("\tBODY").append(loggingInfo.getPayload());
			}
		}

		return builder.toString();
	}

}
