package seo.dale.http.log.client.builder;

import org.springframework.http.HttpHeaders;
import seo.dale.http.client.log.common.LogConstants;
import seo.dale.http.log.client.extractor.ClientRequestLogInfoExtractor;

import java.io.IOException;
import java.util.Optional;

public class ClientRequestLogMessageBuilder {

    private ClientRequestLogInfoExtractor extractor;

    public ClientRequestLogMessageBuilder(ClientRequestLogInfoExtractor extractor) {
        this.extractor = extractor;
    }

    public String build(String messagePrefix) throws IOException {
        StringBuilder builder = new StringBuilder(messagePrefix + "_" + LogConstants.REQUEST_LOG_PREFIX);
        builder.append("\t").append(extractor.extractMethod());
        builder.append("\t").append(extractor.extractUri());

        HttpHeaders httpHeaders = extractor.extractHeaders();
        if (httpHeaders != null && !httpHeaders.isEmpty()) {
            builder.append("\tHEADER").append(httpHeaders);
        }

        Optional.ofNullable(extractor.extractBody())
                .ifPresent(body -> builder.append("\tBODY").append(body));

        return builder.toString();
    }

}
