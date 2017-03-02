package seo.dale.http;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.embedded.RedisServer;
import redis.clients.jedis.Protocol;

import java.io.IOException;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisConnectionFactory connectionFactory(EmbeddedRedisServerBean redisServer) throws IOException {
        System.out.println("Embedded Reids Server is loaded. : " + redisServer);
        return new JedisConnectionFactory();
    }

    @Bean
    public EmbeddedRedisServerBean redisServer() {
        return new EmbeddedRedisServerBean();
    }

    class EmbeddedRedisServerBean implements InitializingBean, DisposableBean {

        private RedisServer redisServer;

        @Override
        public void afterPropertiesSet() throws Exception {
            redisServer = new RedisServer(Protocol.DEFAULT_PORT);
            redisServer.start();
        }

        @Override
        public void destroy() throws Exception {
            if (redisServer != null) {
                redisServer.stop();
            }
        }
    }

}
