package seo.dale.http.web.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RedisDataLoader implements CommandLineRunner {

    private final HttpRecordRepository repo;

    @Autowired
    public RedisDataLoader(HttpRecordRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        addRecord(HttpRequest.Method.GET, "http://jsonplaceholder.typicode.com/posts", "");
        addRecord(HttpRequest.Method.POST, "http://jsonplaceholder.typicode.com/posts", "{\"userId\": 1, \"id\": 101, \"title\": \"foo\", \"body\": \"bar\"}");
        addRecord(HttpRequest.Method.PUT, "http://jsonplaceholder.typicode.com/posts/1", "{\"userId\": 1, \"id\": 101, \"title\": \"foo\", \"body\": \"bar\"}");
        addRecord(HttpRequest.Method.DELETE, "http://jsonplaceholder.typicode.com/posts/1", "");
    }

    private void addRecord(HttpRequest.Method method, String url, String body) {
        HttpRequest request = new HttpRequest();
        request.setMethod(method);
        request.setUrl(url);
        request.setBody(body);

        HttpRecord record = new HttpRecord();
        record.setCreatedDate(new Date());
        record.setRequest(request);

        repo.save(record);
    }

}
