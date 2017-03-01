package seo.dale.http.log.server.extractor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import seo.dale.http.log.server.extractor.body.DefaultRequestBodyExtractor;
import seo.dale.http.log.server.extractor.body.FormUrlencodedRequestBodyExtractor;
import seo.dale.http.log.server.extractor.body.RequestBodyExtractor;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 서블릿 요청 객체로 부터 로그 정보들을 추출해준다
 */
public class RequestLogInfoExtractor {

	private HttpServletRequest request;

	public RequestLogInfoExtractor(HttpServletRequest request) {
		this.request = request;
	}

	public String extractMethod() {
		return request.getMethod();
	}

	public String extractUrl() {
		return request.getRequestURL().toString();
	}

	public String extractQueryString() {
		return request.getQueryString();
	}

	public String extractProtocol() {
		return request.getProtocol();
	}

	public String extractRemoteAddr() {
		return request.getRemoteAddr();
	}

	public String extractRemoteHost() {
		return request.getRemoteHost();
	}

	public String extractRemotePort() {
		return Integer.toString(request.getRemotePort());
	}

	public HttpHeaders extractHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			String value = request.getHeader(name);
			httpHeaders.add(name, value);
		}
		return httpHeaders;
	}

	public String extractBody(int maxPayloadLength) {
		String method = request.getMethod();

		if ("GET".equals(method) || "OPTIONS".equals(method)) {
			return "";
		}

		MediaType mediaType = MediaType.parseMediaType(request.getHeader(HttpHeaders.CONTENT_TYPE));
		RequestBodyExtractor bodyExtractor;

		if ("POST".equals(method) && MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)) {
			bodyExtractor = new FormUrlencodedRequestBodyExtractor();
		} else {
			bodyExtractor = new DefaultRequestBodyExtractor();
		}

		return bodyExtractor.extractBody(request, maxPayloadLength);
	}

}
