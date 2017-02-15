package seo.dale.http.log.server.extractor.body;

import javax.servlet.http.HttpServletRequest;

/**
 * 요청 바디 추출기 인터페이스
 */
public interface RequestBodyExtractor {

	String extractBody(HttpServletRequest request, int maxLength);

}
