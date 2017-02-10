package seo.dale.http.client.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * HttpClient 응답 에러 핸드러
 */
public class HttpClientErrorHandler extends DefaultResponseErrorHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientErrorHandler.class);

	/**
	 * 수신한 응답 메세지를 읽어보니 에러가 있는가?
	 */
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return super.hasError(response);
	}

	/**
	 * 오류 응답이 수신된 경우 어떻게 처리하는가?
	 */
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		HttpStatus httpStatus = response.getStatusCode();
		LOGGER.debug("Handling response error with {}", httpStatus);
		throw new HttpClientException(httpStatus.value() + " " + httpStatus.getReasonPhrase());
	}

}
