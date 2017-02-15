package seo.dale.http.log.client.builder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import seo.dale.http.client.log.common.LogConstants;
import seo.dale.http.log.client.extractor.ClientResponseLogInfoExtractor;

import java.io.IOException;
import java.util.Optional;

public class ClientResponseLogMessageBuilder {

    private ClientResponseLogInfoExtractor extractor;

    public ClientResponseLogMessageBuilder(ClientResponseLogInfoExtractor extractor) {
        this.extractor = extractor;
    }

    public String build(String messagePrefix) throws IOException {
        HttpStatus httpStatus = extractor.extractStatus();

        StringBuilder builder = new StringBuilder(messagePrefix + "_" + LogConstants.RESPONSE_LOG_PREFIX);
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
