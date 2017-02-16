package seo.dale.http.log.client.builder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import seo.dale.http.log.client.extractor.ClientResponseLogInfoExtractor;
import seo.dale.http.log.common.LogConstants;

import java.io.IOException;
import java.util.Optional;

public class ClientResponseLogMessageBuilder {

    private ClientResponseLogInfoExtractor extractor;

    public ClientResponseLogMessageBuilder(ClientResponseLogInfoExtractor extractor) {
        this.extractor = extractor;
    }

    public String build(String messagePrefix) throws IOException {
        HttpStatus httpStatus = extractor.extractStatus();

        StringBuilder builder = new StringBuilder(messagePrefix + "_" + LogConstants.CLIENT_RESPONSE_LOG_PREFIX);
        builder.append("\t").append(httpStatus);

        HttpHeaders httpHeaders = extractor.extractHeaders();
        if (httpHeaders != null && !httpHeaders.isEmpty()) {
            builder.append("\tHEADER").append(httpHeaders);
        }

        Optional.ofNullable(extractor.extractBody())
                .ifPresent(body -> builder.append("\tBODY").append(body));

        return builder.toString();
    }

}
