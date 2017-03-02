package seo.dale.http.web.http;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

// @Service
public class MockHttpRecordService implements HttpRecordService {

    private List<HttpRecord> records;
    private static AtomicLong counter = new AtomicLong();

    private void addRecord(HttpRequest.Method method, String url, String body) {
        HttpRequest request = new HttpRequest();
        request.setMethod(method);
        request.setUrl(url);
        request.setBody(body);

        HttpRecord record = new HttpRecord();
        record.setId(counter.incrementAndGet());
        record.setRequest(request);

        records.add(record);
    }

    public MockHttpRecordService() {
        records = new ArrayList<>();

        addRecord(HttpRequest.Method.GET, "http://jsonplaceholder.typicode.com/posts", "");
        addRecord(HttpRequest.Method.POST, "http://jsonplaceholder.typicode.com/posts", "{\"userId\": 1, \"id\": 101, \"title\": \"foo\", \"body\": \"bar\"}");
        addRecord(HttpRequest.Method.PUT, "http://jsonplaceholder.typicode.com/posts/1", "{\"userId\": 1, \"id\": 101, \"title\": \"foo\", \"body\": \"bar\"}");
        addRecord(HttpRequest.Method.DELETE, "http://jsonplaceholder.typicode.com/posts/1", "");
    }

    @Override
    public List<HttpRecord> findAll() {
        return records;
    }

    @Override
    public void add(HttpRecord record) {
        record.setId(counter.incrementAndGet());
        records.add(record);
    }
}
