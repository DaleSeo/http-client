package seo.dale.http.client.log;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import seo.dale.http.client.log.common.LogUtils;
import seo.dale.http.client.log.model.ClientRequestLogInfo;
import seo.dale.http.client.log.model.ClientResponseLogInfo;
import seo.dale.http.client.log.support.ClientLogMessageBuilder;
import seo.dale.http.client.log.support.ClientLoggingInfoExtractor;

import java.io.IOException;

/**
 * HttpClient 로거
 */
public class HttpClientLogger {

	private ClientLoggingInfoExtractor loggingInfoExtractor;
	private ClientLogMessageBuilder logMessageBuilder;

	private String loggerName;
	private String logLevel;

	public HttpClientLogger(HttpClientLoggerConfig config) {
		this.loggerName = config.getLoggerName();
		this.logLevel = config.getLogLevel();
		loggingInfoExtractor = new ClientLoggingInfoExtractor(config);
		logMessageBuilder = new ClientLogMessageBuilder(config.getMessagePrefix(), config.isSkippableOnSuccess());
	}

	/**
	 * 연동 시스템에 보낼 Request 정보를 로깅한다.
	 */
	public void logClientRequest(HttpRequest request, byte[] body) throws IOException {
		ClientRequestLogInfo loggingInfo = loggingInfoExtractor.extractFromClientRequest(request, body);
		String logMessage = logMessageBuilder.buildRequestLogMessage(loggingInfo);
		printLogMessage(logMessage);
	}

	/**
	 * 연동 시스템으로 부터 받은 Response 정보를 로깅한다.
	 */
	public void logClientResponse(ClientHttpResponse response) throws IOException {
		ClientResponseLogInfo loggingInfo = loggingInfoExtractor.extractFromClientResponse(response);
		String logMessage = logMessageBuilder.buildResponseLogMessage(loggingInfo);
		printLogMessage(logMessage);
	}

	private void printLogMessage(String logMessage) {
		LogUtils.logByLevel(loggerName, logLevel, logMessage);
	}

}
