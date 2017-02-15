package seo.dale.http.log.client;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import seo.dale.http.log.client.builder.ClientRequestLogMessageBuilder;
import seo.dale.http.log.client.builder.ClientResponseLogMessageBuilder;
import seo.dale.http.log.client.extractor.ClientRequestLogInfoExtractor;
import seo.dale.http.log.client.extractor.ClientResponseLogInfoExtractor;
import seo.dale.http.log.common.LogUtils;

import java.io.IOException;

/**
 * HttpClient 로거
 */
public class HttpClientLogger {

	private HttpClientLoggerConfig config;

	public HttpClientLogger(HttpClientLoggerConfig config) {
		this.config = config;
	}

	/**
	 * 연동 시스템에 보낼 Request 정보를 로깅한다.
	 */
	public void logClientRequest(HttpRequest request, byte[] body) throws IOException {
		ClientRequestLogInfoExtractor logInfoExtractor = new ClientRequestLogInfoExtractor(request, body);
		logInfoExtractor.setMaxBodyLength(config.getMaxPayloadLength());
		logInfoExtractor.setPrintPretty(config.isPayloadPretty());

		ClientRequestLogMessageBuilder logMessageBuilder = new ClientRequestLogMessageBuilder(logInfoExtractor);
		String logMessage = logMessageBuilder.build(config.getMessagePrefix());

		printLogMessage(logMessage);
	}

	/**
	 * 연동 시스템으로 부터 받은 Response 정보를 로깅한다.
	 */
	public void logClientResponse(ClientHttpResponse response) throws IOException {
		ClientResponseLogInfoExtractor logInfoExtractor = new ClientResponseLogInfoExtractor(response);
		logInfoExtractor.setSkippableOnSuccess(config.isSkippableOnSuccess());
		logInfoExtractor.setMaxBodyLength(config.getMaxPayloadLength());
		logInfoExtractor.setPrintPretty(config.isPayloadPretty());

		ClientResponseLogMessageBuilder logMessageBuilder = new ClientResponseLogMessageBuilder(logInfoExtractor);
		String logMessage = logMessageBuilder.build(config.getMessagePrefix());

		printLogMessage(logMessage);
	}

	private void printLogMessage(String logMessage) {
		LogUtils.logByLevel(config.getLoggerName(), config.getLogLevel(), logMessage);
	}

}
