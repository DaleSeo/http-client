package seo.dale.http;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import seo.dale.http.log.server.HttpServerLoggerConfig;
import seo.dale.http.server.filter.HttpServerLogFilter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(HttpServerLogFilter httpServerLogFilter) {
        FilterRegistrationBean bean = new FilterRegistrationBean(httpServerLogFilter);
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public HttpServerLogFilter httpServerLogFilter() {
        HttpServerLoggerConfig config = HttpServerLoggerConfig.custom()
                .logLevel("info")
                .build();
        return new HttpServerLogFilter(config);
    }

}
