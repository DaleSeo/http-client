package seo.dale.http;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * works like web.xml
 */
@Configuration
public class ServletConfig extends ServerProperties {

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
