package seo.dale.http.server.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import seo.dale.http.log.server.HttpServerLogger;
import seo.dale.http.log.server.HttpServerLoggerConfig;
import seo.dale.http.log.server.HttpServerLoggerImpl;
import seo.dale.http.server.wrapper.ContentKeepingRequestWrapper;
import seo.dale.http.server.wrapper.ContentKeepingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class HttpServerLogFilter extends OncePerRequestFilter {

    private HttpServerLogger httpServerLogger;

    private boolean requestLoggingEnabled;
    private boolean responseLoggingEnabled;
    private int maxPayloadLength;

    public HttpServerLogFilter(HttpServerLoggerConfig httpServerLoggerConfig) {
        requestLoggingEnabled = httpServerLoggerConfig.isRequestLoggingEnabled();
        responseLoggingEnabled = httpServerLoggerConfig.isResponseLoggingEnabled();
        maxPayloadLength = httpServerLoggerConfig.getMaxBodyLength();

        if (requestLoggingEnabled || responseLoggingEnabled) {
            this.httpServerLogger = new HttpServerLoggerImpl(httpServerLoggerConfig);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // GET 방식이거나 요청 전문까지 로깅하지 않는다면, 구지 요청 객체를 래핑할 필요 없음
        if (!"GET".equals(request.getMethod()) && requestLoggingEnabled && maxPayloadLength > 0) {
            if (!(request instanceof ContentKeepingRequestWrapper)) {
                request = new ContentKeepingRequestWrapper(request);
            }
        }

        if (responseLoggingEnabled && maxPayloadLength > 0) {
            if (!(response instanceof ContentKeepingResponseWrapper)) {
                response = new ContentKeepingResponseWrapper(response);
            }
        }

        if (requestLoggingEnabled) httpServerLogger.logRequest(request);

        filterChain.doFilter(request, response);

        if (responseLoggingEnabled) httpServerLogger.logResponse(request, response);
    }

}
