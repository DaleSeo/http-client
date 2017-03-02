package seo.dale.http.web.http;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Protocol;
import redis.embedded.RedisServer;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class HttpRecordRepositoryTest {

    private RedisServer redisServer;

    @Autowired
    private HttpRecordRepository repo;

    @Before
    public void setUp() throws IOException {
        redisServer = new RedisServer(Protocol.DEFAULT_PORT);
        redisServer.start();
    }

    @Test
    public void test() throws Exception {
        HttpRequest request = new HttpRequest();
        request.setMethod(HttpRequest.Method.PUT);
        request.setUrl("http://jsonplaceholder.typicode.com/posts/3");
        request.setBody("{\"userId\": 1, \"id\": 101, \"title\": \"foo\", \"body\": \"bar\"}");

        HttpRecord saved = new HttpRecord();
        saved.setRequest(request);

        repo.save(saved);

        HttpRecord found = repo.findOne(saved.getId());
        System.out.println("### " + found);
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(repo.count()).isEqualTo(1);

        repo.delete(saved);
        assertThat(repo.count()).isEqualTo(0);
    }

    @After
    public void tearDown() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    @Configuration
    @EnableRedisRepositories
    static class Config {

        @Bean
        RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
            RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
            template.setConnectionFactory(connectionFactory);
            return template;
        }

        @Bean
        RedisConnectionFactory connectionFactory() throws IOException {
            return new JedisConnectionFactory();
        }
    }

}