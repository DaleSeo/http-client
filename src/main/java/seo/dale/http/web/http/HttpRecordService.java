package seo.dale.http.web.http;

import java.util.List;

public interface HttpRecordService {

    List<HttpRecord> findAll();

    void add(HttpRecord record);

}
