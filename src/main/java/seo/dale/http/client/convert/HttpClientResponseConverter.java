package seo.dale.http.client.convert;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import seo.dale.http.client.model.HttpClientResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 스프링의 ResponseEntity를 라이브러리의 HttpClient 응답 모델로 변환한다.
 */
public class HttpClientResponseConverter<O> {

	public HttpClientResponse<O> convert(ResponseEntity<O> entity) {
		HttpClientResponse<O> res = new HttpClientResponse();

		// status
		HttpStatus httpStatus = entity.getStatusCode();
		if (httpStatus != null) {
			HttpClientResponse.Status status = new HttpClientResponse.Status();
			status.setCode(httpStatus.value());
			status.setText(httpStatus.getReasonPhrase());
			res.setStatus(status);
		}

		// body
		res.setBody(entity.getBody());

		// headers
		Map<String, List<String>> headers = new HashMap<>();
		HttpHeaders httpHeaders = entity.getHeaders();
		httpHeaders.entrySet().stream().forEach(entry ->
			headers.put(entry.getKey(), entry.getValue()));

		res.setHeaders(headers);

		return res;
	}

}
