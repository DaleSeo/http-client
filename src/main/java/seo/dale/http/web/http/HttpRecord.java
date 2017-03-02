package seo.dale.http.web.http;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@RedisHash("records")
@Data
public class HttpRecord {

    @Id
    private Long id;

    @Indexed
    private Date createdDate;

    private HttpRequest request;
    private HttpResponse response;

    public HttpRecord() {
    }

    public HttpRecord(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
    }

}
