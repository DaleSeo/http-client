package seo.dale.http.log.client.extractor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import seo.dale.http.log.common.LogUtils;

import java.io.IOException;
import java.net.URI;

public class ClientRequestLogInfoExtractor {

    private HttpRequest request;
    private byte[] body;

    private int maxBodyLength = Integer.MAX_VALUE;
    private boolean printPretty = true;

    public ClientRequestLogInfoExtractor(HttpRequest request, byte[] body) {
        this.request = request;
        this.body = body;
    }

    public void setMaxBodyLength(int maxBodyLength) {
        this.maxBodyLength = maxBodyLength == -1 ? Integer.MAX_VALUE : maxBodyLength; // -1이 들어오면 Full Body Logging
    }

    public void setPrintPretty(boolean printPretty) {
        this.printPretty = printPretty;
    }

    public HttpMethod extractMethod() {
        return request.getMethod();
    }

    public URI extractUri() {
        return request.getURI();
    }

    public HttpHeaders extractHeaders() {
        return request.getHeaders();
    }

    public String extractBody() throws IOException {
        HttpMethod method = request.getMethod();

        if (method == HttpMethod.GET || maxBodyLength == 0) {
            return null;
        }

        return LogUtils.readWithInMaxLength(body, maxBodyLength, printPretty);
    }

}
