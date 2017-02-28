package seo.dale.http.web.http;

import lombok.Data;

@Data
public class HttpRecord {

    private Long id;
    private HttpRequest request;
    private HttpResponse response;

    public HttpRecord() {
    }

    public HttpRecord(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
    }

}
