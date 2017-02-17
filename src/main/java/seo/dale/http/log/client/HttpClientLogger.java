package seo.dale.http.log.client;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import seo.dale.http.log.client.builder.HttpClientLogMessageBuilder;
import seo.dale.http.log.client.builder.HttpClientLogMessageBuilderCurl;
import seo.dale.http.log.client.builder.HttpClientLogMessageBuilderSingleLine;
import seo.dale.http.log.client.extractor.ClientRequestLogInfoExtractor;
import seo.dale.http.log.client.extractor.ClientResponseLogInfoExtractor;
import seo.dale.http.log.common.LogUtils;

import java.io.IOException;

/**
 * HttpClient 로거
 */
public class HttpClientLogger {

	private HttpClientLoggerConfig config;

	private HttpClientLogMessageBuilder logMessageBuilder;

	public HttpClientLogger(HttpClientLoggerConfig config) {
		this.config = config;
		if (config.getLogStyle() == HttpClientLoggerConfig.LogStyle.SINGLE_LINE) {
			logMessageBuilder = new HttpClientLogMessageBuilderSingleLine(config.getMessagePrefix());
		} else {
			logMessageBuilder = new HttpClientLogMessageBuilderCurl(config.getMessagePrefix());
		}
	}

	/**
	 * 연동 시스템에 보낼 Request 정보를 로깅한다.
	 */
	public void logClientRequest(HttpRequest request, byte[] body) throws IOException {
		ClientRequestLogInfoExtractor logInfoExtractor = new ClientRequestLogInfoExtractor(request, body);
		logInfoExtractor.setMaxBodyLength(config.getMaxPayloadLength());
		logInfoExtractor.setPrintPretty(config.isPayloadPretty());

		String logMessage = logMessageBuilder.buildForRequest(logInfoExtractor);

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

		String logMessage = logMessageBuilder.buildForResponse(logInfoExtractor);

		printLogMessage(logMessage);
	}

	private void printLogMessage(String logMessage) {
		LogUtils.logByLevel(config.getLoggerName(), config.getLogLevel(), logMessage);
	}

}
