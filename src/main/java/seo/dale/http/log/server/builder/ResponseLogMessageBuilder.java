package seo.dale.http.log.server.builder;

import seo.dale.http.log.server.extractor.ResponseLogInfoExtractor;

/**
 * 응답 로그 메세지 빌더 인터페이스
 */
public interface ResponseLogMessageBuilder {

	String build(ResponseLogInfoExtractor responseInfoExtractor);

}
