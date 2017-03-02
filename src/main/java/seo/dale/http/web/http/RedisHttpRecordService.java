package seo.dale.http.web.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisHttpRecordService implements HttpRecordService {

    @Autowired
    private HttpRecordRepository repo;

    private void addRecord(HttpRequest.Method method, String url, String body) {
        HttpRequest request = new HttpRequest();
        request.setMethod(method);
        request.setUrl(url);
        request.setBody(body);

        HttpRecord record = new HttpRecord();
        record.setRequest(request);

        repo.save(record);
    }

    public RedisHttpRecordService() {
        addRecord(HttpRequest.Method.GET, "http://jsonplaceholder.typicode.com/posts", "");
        addRecord(HttpRequest.Method.POST, "http://jsonplaceholder.typicode.com/posts", "{\"userId\": 1, \"id\": 101, \"title\": \"foo\", \"body\": \"bar\"}");
        addRecord(HttpRequest.Method.PUT, "http://jsonplaceholder.typicode.com/posts/1", "{\"userId\": 1, \"id\": 101, \"title\": \"foo\", \"body\": \"bar\"}");
        addRecord(HttpRequest.Method.DELETE, "http://jsonplaceholder.typicode.com/posts/1", "");
    }

    @Override
    public List<HttpRecord> findAll() {
        return null;
    }

    @Override
    public void add(HttpRecord record) {

    }

}
