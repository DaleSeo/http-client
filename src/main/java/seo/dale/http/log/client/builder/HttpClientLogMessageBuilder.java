package seo.dale.http.log.client.builder;

import seo.dale.http.log.client.extractor.ClientRequestLogInfoExtractor;
import seo.dale.http.log.client.extractor.ClientResponseLogInfoExtractor;

public interface HttpClientLogMessageBuilder {

    String buildForRequest(ClientRequestLogInfoExtractor extractor);

    String buildForResponse(ClientResponseLogInfoExtractor extractor);

}
