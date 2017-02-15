package seo.dale.http.log.server.extractor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.WebUtils;
import seo.dale.http.server.wrapper.ContentKeepingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * 서블릿 응답 객체로 부터 로그 정보들을 추출해준다
 */
public class ResponseLogInfoExtractor {

	private HttpServletRequest request;
	private HttpServletResponse response;

	public ResponseLogInfoExtractor(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public String extractProtocol() {
		return request.getProtocol();
	}

	public HttpStatus extractStatus() {
		return HttpStatus.valueOf(response.getStatus());
	}

	public HttpHeaders extractHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		Collection<String> headerNames = response.getHeaderNames();
		for (String name : headerNames) {
			Collection<String> values = response.getHeaders(name);
			for (String value : values) {
				httpHeaders.add(name, value);
			}
		}
		return httpHeaders;
	}

	public String extractBody(int maxPayloadLength) {
		ContentKeepingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentKeepingResponseWrapper.class);
		if (wrapper == null) {
			return "";
		}

		String content = wrapper.getContentAsString();
		if (content.length() == 0) {
			return "";
		}

		int length = Math.min(content.length(), maxPayloadLength);

		return content.substring(0, length);
	}

}
