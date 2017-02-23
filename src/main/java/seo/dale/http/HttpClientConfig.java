package seo.dale.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import seo.dale.http.client.HttpClient;
import seo.dale.http.client.HttpClientSpring;
import seo.dale.http.client.intercept.LogInterceptor;
import seo.dale.http.client.resolve.BaseUrlResolver;
import seo.dale.http.client.resolve.HeaderResolver;
import seo.dale.http.client.template.RestTemplateBuilder;
import seo.dale.http.log.client.HttpClientLoggerConfig;

import java.util.Collections;
import java.util.List;

@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient sacClient(RestTemplate restTemplate, BaseUrlResolver baseUrlResolver, HeaderResolver headerResolver) {
        HttpClientSpring sacClient = new HttpClientSpring(restTemplate, baseUrlResolver, headerResolver);
        return sacClient;
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpClientLoggerConfig config = HttpClientLoggerConfig
                .custom()
                .build();
        RestTemplate restTemplate = new RestTemplateBuilder()
                .interceptors(Collections.singletonList(new LogInterceptor(config)))
                .build();
        return restTemplate;
    }

    @Bean
    public BaseUrlResolver baseUrlResolver() {
        return () -> "https://jsonplaceholder.typicode.com";
    }

    @Bean
    public HeaderResolver headerResolver() {
        return new HeaderResolver() {
            @Override
            public String resolveAuthKey() {
                return "85f1f03";
            }

            @Override
            public MediaType resolveContentType() {
                return MediaType.APPLICATION_JSON_UTF8;
            }

            @Override
            public List<MediaType> resolveAccept() {
                return Collections.singletonList(MediaType.APPLICATION_JSON_UTF8);
            }
        };
    }

}
