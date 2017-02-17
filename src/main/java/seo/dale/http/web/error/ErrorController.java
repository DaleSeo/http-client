package seo.dale.http.web.error;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @see seo.dale.http.ServletConfig#customize(ConfigurableEmbeddedServletContainer)
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

	@RequestMapping("/{statusCode}")
	public @ResponseBody String handleStatusCode(@PathVariable String statusCode) {
		return "Error Status Code: " + statusCode;
	}

	@RequestMapping("/")
	public @ResponseBody String handle(Exception exception, HttpServletRequest request, HttpServletResponse response) {
		return "UNKNOWN ERROR";
	}

}
