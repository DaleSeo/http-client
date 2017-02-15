package seo.dale.http.log.server;

import seo.dale.http.client.log.common.LogUtils;
import seo.dale.http.log.server.builder.RequestLogMessageBuilder;
import seo.dale.http.log.server.builder.ResponseLogMessageBuilder;
import seo.dale.http.log.server.builder.ServletLogMessageBuilderFactory;
import seo.dale.http.log.server.extractor.RequestLogInfoExtractor;
import seo.dale.http.log.server.extractor.ResponseLogInfoExtractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServerLoggerImpl implements HttpServerLogger {

	/** 로거 이름 */
	private String loggerName;

	/** 로그 레벨 */
	private String logLevel = "debug";

	private ServletLogMessageBuilderFactory logMessageBuilderFactory;

	private RequestLogMessageBuilder requestLogMessageBuilder;

	private ResponseLogMessageBuilder responseLogMessageBuilder;

	public HttpServerLoggerImpl(HttpServerLoggerConfig config) {
		this.loggerName = config.getLoggerName();
		this.logLevel = config.getLogLevel();

		logMessageBuilderFactory = new ServletLogMessageBuilderFactory(config.isShouldIncludeHeaders(), config.getMaxPayloadLength());
		requestLogMessageBuilder = logMessageBuilderFactory.createForRequest(config.getStyle());
		responseLogMessageBuilder = logMessageBuilderFactory.createForResponse(config.getStyle());
	}

	public HttpServerLoggerImpl(String loggerName, String logLevel, boolean shouldIncludeHeaders, int maxPayloadLength, HttpServerLogStyle loggingStyle) {
		this.loggerName = loggerName;
		this.logLevel = logLevel;

		if (loggingStyle == null) {
			loggingStyle = HttpServerLogStyle.CURL;
		}

		logMessageBuilderFactory = new ServletLogMessageBuilderFactory(shouldIncludeHeaders, maxPayloadLength);
		requestLogMessageBuilder = logMessageBuilderFactory.createForRequest(loggingStyle);
		responseLogMessageBuilder = logMessageBuilderFactory.createForResponse(loggingStyle);
	}

	public void logRequest(HttpServletRequest request) {
		String logMessage = requestLogMessageBuilder.build(new RequestLogInfoExtractor(request));
		LogUtils.logByLevel(loggerName, logLevel, logMessage);
	}

	public void logResponse(HttpServletRequest request, HttpServletResponse response) {
		String logMessage = responseLogMessageBuilder.build(new ResponseLogInfoExtractor(request, response));
		LogUtils.logByLevel(loggerName, logLevel, logMessage);
	}

}
