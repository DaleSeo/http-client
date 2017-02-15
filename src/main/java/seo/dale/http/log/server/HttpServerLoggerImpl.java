package seo.dale.http.log.server;

import org.slf4j.Logger;
import seo.dale.http.log.common.LogUtils;
import seo.dale.http.log.server.builder.RequestLogMessageBuilder;
import seo.dale.http.log.server.builder.ResponseLogMessageBuilder;
import seo.dale.http.log.server.builder.ServletLogMessageBuilderFactory;
import seo.dale.http.log.server.extractor.RequestLogInfoExtractor;
import seo.dale.http.log.server.extractor.ResponseLogInfoExtractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServerLoggerImpl implements HttpServerLogger {

	/** 로거 */
	private Logger logger;

	/** 로그 레벨 */
	private String logLevel = "debug";

	private ServletLogMessageBuilderFactory logMessageBuilderFactory;

	private RequestLogMessageBuilder requestLogMessageBuilder;

	private ResponseLogMessageBuilder responseLogMessageBuilder;

	public HttpServerLoggerImpl(HttpServerLoggerConfig config) {
		this.logger = config.getLogger();
		this.logLevel = config.getLogLevel();

		logMessageBuilderFactory = new ServletLogMessageBuilderFactory(config.isShouldIncludeHeaders(), config.getMaxBodyLength());
		requestLogMessageBuilder = logMessageBuilderFactory.createForRequest(config.getStyle());
		responseLogMessageBuilder = logMessageBuilderFactory.createForResponse(config.getStyle());
	}

	public void logRequest(HttpServletRequest request) {
		String logMessage = requestLogMessageBuilder.build(new RequestLogInfoExtractor(request));
		LogUtils.logByLevel(logger, logLevel, logMessage);
	}

	public void logResponse(HttpServletRequest request, HttpServletResponse response) {
		String logMessage = responseLogMessageBuilder.build(new ResponseLogInfoExtractor(request, response));
		LogUtils.logByLevel(logger, logLevel, logMessage);
	}

}
