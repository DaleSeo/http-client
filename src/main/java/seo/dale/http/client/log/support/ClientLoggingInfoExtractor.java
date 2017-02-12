package seo.dale.http.client.log.support;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import seo.dale.http.client.log.model.ClientRequestLogInfo;
import seo.dale.http.client.log.model.ClientResponseLogInfo;
import seo.dale.http.client.log.HttpClientLoggerConfig;
import seo.dale.http.client.log.common.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * 클라이언트 로깅 정보 추출기
 */
public class ClientLoggingInfoExtractor {

	private int maxPayloadLength;
	private boolean payloadPretty;

	public ClientLoggingInfoExtractor(HttpClientLoggerConfig config) {
		this.maxPayloadLength = config.getMaxPayloadLength() == -1 ? Integer.MAX_VALUE : config.getMaxPayloadLength(); // -1이 들어오면 Full Body Logging
		payloadPretty = config.isPayloadPretty();
	}

	/**
	 * 클라이언트 요청 객체로 부터 로깅 정보를 추출한다.
	 */
	public ClientRequestLogInfo extractFromClientRequest(HttpRequest request, byte[] body) throws IOException {
		HttpMethod method = request.getMethod();
		URI uri = request.getURI();
		HttpHeaders httpHeaders = request.getHeaders();

		ClientRequestLogInfo requestLoggingInfo = new ClientRequestLogInfo();
		requestLoggingInfo.setMethod(method);
		requestLoggingInfo.setUri(uri);
		requestLoggingInfo.setHttpHeaders(httpHeaders);

		// POST 방식이고 maxPayloadLength 설정이 0보다 크면 Request Body 로그를 최대 길이 만큼 출력한다.
		if (method == HttpMethod.POST && maxPayloadLength > 0) {
			String payload = LogUtils.readWithInMaxLength(body, maxPayloadLength, payloadPretty);
			requestLoggingInfo.setPayload(payload);
		}

		return requestLoggingInfo;
	}

	/**
	 * 클라이언트 응답 객체로 부터 로깅 정보를 추출한다.
	 */
	public ClientResponseLogInfo extractFromClientResponse(ClientHttpResponse response) throws IOException {
		HttpHeaders httpHeaders = response.getHeaders();
		HttpStatus httpStatus = response.getStatusCode();
		InputStream body = response.getBody();

		ClientResponseLogInfo responseLoggingInfo = new ClientResponseLogInfo();
		responseLoggingInfo.setHttpStatus(httpStatus);
		responseLoggingInfo.setHttpHeaders(httpHeaders);

		if (maxPayloadLength > 0) {
			String payload = LogUtils.readWithInMaxLength(body, maxPayloadLength, payloadPretty);
			if (StringUtils.isNotEmpty(payload)) {
				responseLoggingInfo.setPayload(payload);
			}
		}

		return responseLoggingInfo;
	}
}
