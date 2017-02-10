package seo.dale.http.client.intercept;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/**
 * 필수 헤더 보완 인터셉터
 */
public class HeaderInterceptor implements ClientHttpRequestInterceptor {

	/**
	 * 모든 요청에 대해서 누락된 필수 헤더를 세팅해준다.
	 */
	@Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		HttpHeaders httpHeaders = request.getHeaders();

		// UUID 헤더가 누락되어 있는 경우 생산하여 세팅
		if (StringUtils.isBlank(httpHeaders.getFirst("UUID"))) {
			httpHeaders.set("UUID", UUID.randomUUID().toString());
		}

		// Accept 헤더가 누락되어 있는 경우 "*/*"로 세팅
		if (CollectionUtils.isEmpty(httpHeaders.getAccept())) {
			httpHeaders.setAccept(Arrays.asList(MediaType.ALL));
		}

		// 요청 메세지가 있는데 Content-type 헤더가 누락되어 있는 경우 "application/json;charset=UTF-8"로 세팅
		if (body != null && body.length != 0 && httpHeaders.getContentType() == null) {
			httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		}

		return execution.execute(request, body);
    }

}
