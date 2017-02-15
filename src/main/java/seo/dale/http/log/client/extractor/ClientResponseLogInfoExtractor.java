package seo.dale.http.log.client.extractor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import seo.dale.http.log.common.LogUtils;

import java.io.IOException;

public class ClientResponseLogInfoExtractor {

    private ClientHttpResponse response;

    boolean skippableOnSuccess = false;
    private int maxBodyLength = Integer.MAX_VALUE;
    private boolean printPretty = true;

    public ClientResponseLogInfoExtractor(ClientHttpResponse response) {
        this.response = response;
    }

    public void setSkippableOnSuccess(boolean skippableOnSuccess) {
        this.skippableOnSuccess = skippableOnSuccess;
    }

    public void setMaxBodyLength(int maxBodyLength) {
        this.maxBodyLength = maxBodyLength == -1 ? Integer.MAX_VALUE : maxBodyLength; // -1이 들어오면 Full Body Logging
    }

    public void setPrintPretty(boolean printPretty) {
        this.printPretty = printPretty;
    }

    public HttpStatus extractStatus() throws IOException {
        return response.getStatusCode();
    }

    public HttpHeaders extractHeaders() {
        return response.getHeaders();
    }

    public String extractBody() throws IOException {
        boolean successful = response.getStatusCode().is2xxSuccessful();
        if ((skippableOnSuccess && successful) || maxBodyLength == 0) {
            return null;
        }
        return LogUtils.readWithInMaxLength(response.getBody(), maxBodyLength, printPretty);
    }

}
