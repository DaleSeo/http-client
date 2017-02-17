package seo.dale.http;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import seo.dale.http.log.server.HttpServerLoggerConfig;
import seo.dale.http.server.filter.ErrorHandlingFilter;
import seo.dale.http.server.filter.ErrorTestFilter;
import seo.dale.http.server.filter.HttpServerLogFilter;

@Configuration
public class WebConfig {

	@Bean
	public FilterRegistrationBean errorHandlingFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean(new ErrorHandlingFilter());
		bean.addUrlPatterns("/*");
		bean.setOrder(0);
		return bean;
	}

    @Bean
    public FilterRegistrationBean httpServerLogFilter() {
	    HttpServerLoggerConfig config = HttpServerLoggerConfig.custom()
			    .logLevel("info")
			    .build();

        FilterRegistrationBean bean = new FilterRegistrationBean(new HttpServerLogFilter(config));
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean errorTestFilter() {
	    FilterRegistrationBean bean = new FilterRegistrationBean(new ErrorTestFilter());
	    bean.addUrlPatterns("/*");
	    bean.setOrder(2);
	    // bean.setEnabled(false);
	    return bean;
    }

}
