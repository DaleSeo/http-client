package seo.dale.http.log.client.builder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import seo.dale.http.log.client.extractor.ClientRequestLogInfoExtractor;
import seo.dale.http.log.client.extractor.ClientResponseLogInfoExtractor;
import seo.dale.http.log.common.LogConstants;

import java.util.Optional;

public class HttpClientLogMessageBuilderSingleLine implements HttpClientLogMessageBuilder {

    private String messagePrefix;

    public HttpClientLogMessageBuilderSingleLine(String messagePrefix) {
        this.messagePrefix = messagePrefix;
    }

    public String buildForRequest(ClientRequestLogInfoExtractor extractor) {
        // Title
        String title = LogConstants.CLIENT_REQUEST_LOG_PREFIX;
        if (StringUtils.isNotBlank(messagePrefix)) {
            title = messagePrefix + "_" + title;
        }
        StringBuilder builder = new StringBuilder(title);

        // Request Line
        builder.append("\t").append(extractor.extractMethod());
        builder.append("\t").append(extractor.extractUri());

        // Headers
        HttpHeaders httpHeaders = extractor.extractHeaders();
        if (httpHeaders != null && !httpHeaders.isEmpty()) {
            builder.append("\tHEADER").append(httpHeaders);
        }

        // Body
        Optional.ofNullable(extractor.extractBody())
                .ifPresent(body -> builder.append("\tBODY").append(body));

        return builder.toString();
    }

    public String buildForResponse(ClientResponseLogInfoExtractor extractor) {
        // Title
        String title = LogConstants.CLIENT_RESPONSE_LOG_PREFIX;
        if (StringUtils.isNotBlank(messagePrefix)) {
            title = messagePrefix + "_" + title;
        }
        StringBuilder builder = new StringBuilder(title);

        // Status Line
        HttpStatus httpStatus = extractor.extractStatus();
        builder.append("\t").append(httpStatus);

        // Headers
        HttpHeaders httpHeaders = extractor.extractHeaders();
        if (httpHeaders != null && !httpHeaders.isEmpty()) {
            builder.append("\tHEADER").append(httpHeaders);
        }

        // Body
        Optional.ofNullable(extractor.extractBody())
                .ifPresent(body -> builder.append("\tBODY").append(body));

        return builder.toString();
    }


}
