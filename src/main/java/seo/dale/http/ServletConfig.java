package seo.dale.http;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import seo.dale.http.log.server.HttpServerLoggerConfig;
import seo.dale.http.server.filter.ErrorHandlingFilter;
import seo.dale.http.server.filter.ErrorTestFilter;
import seo.dale.http.server.filter.HttpServerLogFilter;

/**
 * works like web.xml
 */
@Configuration
public class ServletConfig extends ServerProperties {

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

	/**
	 * @see seo.dale.http.web.error.ErrorController
	 */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		super.customize(container);
		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
		container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
		// TODO: doesn't work below
		container.addErrorPages(new ErrorPage(Exception.class,"/error"));
		container.addErrorPages(new ErrorPage("/error"));
	}

}
