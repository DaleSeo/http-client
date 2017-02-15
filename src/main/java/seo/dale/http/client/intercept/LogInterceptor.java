package seo.dale.http.client.intercept;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import seo.dale.http.log.client.HttpClientLogger;
import seo.dale.http.log.client.HttpClientLoggerConfig;

import java.io.IOException;

/**
 * 로깅 인터셉터
 */
public class LogInterceptor implements ClientHttpRequestInterceptor {

	private HttpClientLogger logger;

	public LogInterceptor(HttpClientLoggerConfig config) {
		this.logger = new HttpClientLogger(config);
	}

	@Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		logger.logClientRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		logger.logClientResponse(response);
        return response;
    }

}
