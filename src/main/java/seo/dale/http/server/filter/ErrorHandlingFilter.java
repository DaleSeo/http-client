package seo.dale.http.server.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorHandlingFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandlingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			// response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			logger.warn(e.toString(), e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			PrintWriter writer = response.getWriter();
			writer.write("Filter Error: " + e.getMessage());
		}
	}

}
