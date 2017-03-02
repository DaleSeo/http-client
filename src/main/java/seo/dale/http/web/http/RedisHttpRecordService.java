package seo.dale.http.web.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RedisHttpRecordService implements HttpRecordService {

    @Autowired
    private HttpRecordRepository repo;

    @Override
    public List<HttpRecord> findAll() {
        List<HttpRecord> list = new ArrayList<>();
        repo.findAll().forEach(list::add);
        return list;
    }

    @Override
    public void add(HttpRecord record) {
        record.setCreatedDate(new Date());
        repo.save(record);
    }

}
