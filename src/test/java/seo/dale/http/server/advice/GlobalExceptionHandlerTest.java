package seo.dale.http.server.advice;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.OncePerRequestFilter;
import seo.dale.http.web.test.ErrorTestController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionHandlerTest {

    private MockMvc mvc;

    @Before
    public void setUp() {

        mvc = MockMvcBuilders.standaloneSetup(new ErrorTestController())
		        .addFilter(new OncePerRequestFilter() {
			        @Override
			        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
				        throw new RuntimeException("Filter Exception");
			        }
		        })
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGet() throws Exception {
        mvc.perform(
                get("/error/runtime")
        )
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

}