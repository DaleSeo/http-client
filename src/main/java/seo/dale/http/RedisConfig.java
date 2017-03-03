package seo.dale.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.clients.util.JedisURIHelper;
import redis.embedded.RedisServer;
import redis.clients.jedis.Protocol;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    @Profile("production")
    public RedisConnectionFactory connectionFactoryForProduction() throws IOException, URISyntaxException {
        URI redisURI = new URI(System.getenv("REDIS_URL"));
        logger.debug("Use Heroku Redis Server. ({})", redisURI);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisURI.getHost());
        jedisConnectionFactory.setPort(redisURI.getPort());
        jedisConnectionFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);
        logger.debug("Redis Database: " + JedisURIHelper.getDBIndex(redisURI));
        jedisConnectionFactory.setDatabase(JedisURIHelper.getDBIndex(redisURI));
        logger.debug("Redis Password: " + JedisURIHelper.getPassword(redisURI));
        jedisConnectionFactory.setPassword(JedisURIHelper.getPassword(redisURI));
        return new JedisConnectionFactory();
    }

    @Bean
    @Profile("!production")
    public RedisConnectionFactory connectionFactory(EmbeddedRedisServerBean redisServer) throws IOException {
        logger.debug("Use Embedded Redis Server." + redisServer);
        return new JedisConnectionFactory();
    }

    @Bean
    @Profile("!production")
    public EmbeddedRedisServerBean redisServer() {
        return new EmbeddedRedisServerBean();
    }

    class EmbeddedRedisServerBean implements InitializingBean, DisposableBean {

        private RedisServer redisServer;

        @Override
        public void afterPropertiesSet() throws Exception {
            redisServer = new RedisServer(Protocol.DEFAULT_PORT);
            redisServer.start();
            logger.debug("Embedded Redis server has started.");
        }

        @Override
        public void destroy() throws Exception {
            if (redisServer != null) {
                redisServer.stop();
                logger.debug("Embedded Redis server has stopped.");
            }
        }
    }

}
