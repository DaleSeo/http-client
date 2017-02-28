package seo.dale.http.web.http;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockHttpHistoryService implements HttpHistoryService {

    private List<HttpRecord> records;

    private void addRecord(HttpRequest.Method method, String host, String path, String body) {
        HttpRequest.Url url0 = new HttpRequest.Url();
        url0.setScheme(HttpRequest.Scheme.HTTP);
        url0.setHost(host);
        url0.setPath(path);

        HttpRequest request0 = new HttpRequest();
        request0.setMethod(method);
        request0.setUrl(url0);
        request0.setBody(body);

        HttpRecord record0 = new HttpRecord();
        record0.setRequest(request0);

        records.add(record0);
    }

    public MockHttpHistoryService() {
        records = new ArrayList<>();

        addRecord(HttpRequest.Method.GET, "jsonplaceholder.typicode.com", "/posts", "");
        addRecord(HttpRequest.Method.POST, "jsonplaceholder.typicode.com", "/posts", "{\"userId\": 1, \"id\": 101, \"title\": \"foo\", \"body\": \"bar\"}");
        addRecord(HttpRequest.Method.PUT, "jsonplaceholder.typicode.com", "/posts/1", "{\"userId\": 1, \"id\": 101, \"title\": \"foo\", \"body\": \"bar\"}");
        addRecord(HttpRequest.Method.DELETE, "jsonplaceholder.typicode.com", "/posts/1", "");
    }

    @Override
    public List<HttpRecord> findAll() {
        return records;
    }

    @Override
    public void add(HttpRecord record) {
        records.add(record);
    }
}
