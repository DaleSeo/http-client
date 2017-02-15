package seo.dale.http.log.server.builder;

import seo.dale.http.log.server.extractor.RequestLogInfoExtractor;

/**
 * 요청 로그 메세지 빌더 인터페이스
 */
public interface RequestLogMessageBuilder {

	String build(RequestLogInfoExtractor requestInfoExtractor);

}
