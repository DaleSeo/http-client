package seo.dale.http.server.advice;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionHandlerTest {

    private MockMvc mvc;

    @Before
    public void setUp() {

        mvc = MockMvcBuilders.standaloneSetup(new MockController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGet() throws Exception {
        mvc.perform(
                get("/error")
        )
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("java.lang.RuntimeException: TEST ERROR"));
    }

    @Controller
    static class MockController {
        @RequestMapping("/error")
        public void error() {
            throw new RuntimeException("TEST ERROR");
        }
    }

}