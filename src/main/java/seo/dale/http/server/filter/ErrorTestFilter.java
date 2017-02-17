package seo.dale.http.server.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorTestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if ("/error/filter".equals(request.getRequestURI())) {
			throw new RuntimeException("Filter Error Beforehand");
		}
		filterChain.doFilter(request, response);
		if ("/error/filter/after".equals(request.getRequestURI())) {
			throw new RuntimeException("Filter Error Afterward");
		}
	}

}
