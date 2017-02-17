package seo.dale.http.log.client.builder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import seo.dale.http.log.client.extractor.ClientRequestLogInfoExtractor;
import seo.dale.http.log.client.extractor.ClientResponseLogInfoExtractor;
import seo.dale.http.log.common.LogConstants;

import java.util.Optional;

public class HttpClientLogMessageBuilderCurl implements HttpClientLogMessageBuilder {

    private String messagePrefix;

    public HttpClientLogMessageBuilderCurl(String messagePrefix) {
        this.messagePrefix = messagePrefix;
    }

    public String buildForRequest(ClientRequestLogInfoExtractor extractor) {
        StringBuilder builder = new StringBuilder();

        // Title
        String title = LogConstants.CLIENT_REQUEST_LOG_PREFIX;
        if (StringUtils.isNotBlank(messagePrefix)) {
            title = messagePrefix + "_" + title;
        }
        builder.append("[[").append(title).append("]]");

        // Request Line
        builder.append("\n")
                .append(extractor.extractMethod())
                .append(" ")
                .append(extractor.extractUri());

        // Headers
        HttpHeaders headers = extractor.extractHeaders();
        appendHeaders(builder, headers);

        // Body
        String body = extractor.extractBody();
        appendBody(builder, body);

        return builder.toString();
    }

    public String buildForResponse(ClientResponseLogInfoExtractor extractor) {
        StringBuilder builder = new StringBuilder();

        // Title
        String title = LogConstants.CLIENT_RESPONSE_LOG_PREFIX;
        if (StringUtils.isNotBlank(messagePrefix)) {
            title = messagePrefix + "_" + title;
        }
        builder.append("[[").append(title).append("]]");

        // Status Line
        HttpStatus httpStatus = extractor.extractStatus();
        builder.append("\n").append(httpStatus.value() + " " + httpStatus.getReasonPhrase());

        // Headers
        HttpHeaders headers = extractor.extractHeaders();
        appendHeaders(builder, headers);

        // Body
        String body = extractor.extractBody();
        appendBody(builder, body);

        return builder.toString();
    }

    private void appendHeaders(StringBuilder builder, HttpHeaders headers) {
        if (headers != null && !headers.isEmpty()) {
            builder.append("\n");
        }

        for (String name : headers.keySet()) {
            String value = headers.getFirst(name);
            builder.append("> ").append(name).append(": ").append(value).append("\n");
        }

        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }
    }

    private void appendBody(StringBuilder builder, String body) {
        Optional.ofNullable(body)
                .ifPresent(b -> builder.append("\n").append(b));
    }

}
