package seo.dale.http.server.filter;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;
import seo.dale.http.log.server.HttpServerLoggerConfig;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HttpServerLogFilterTest {

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        HttpServerLoggerConfig config = HttpServerLoggerConfig.custom()
                .logger(LoggerFactory.getLogger("seo.dale.http"))
                .responseLoggingEnabled(true)
                .logLevel("info")
                .shouldIncludeHeaders(true)
                .maxBodyLength(100)
                .build();

        HttpServerLogFilter filter = new HttpServerLogFilter(config);

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);

        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setMessageConverters(stringConverter, new MappingJackson2HttpMessageConverter())
                .addFilter(filter)
                .build();
    }

    @Test
    public void testGetText() throws Exception {
        mockMvc.perform(
                get("/text?id=123&name=Dale%20Seo&email=dale.seo@gmail.com")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .accept(new MediaType("text", "plain", StandardCharsets.UTF_8))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testPostText() throws Exception {
        mockMvc.perform(
                post("/text")
                        .param("id", "123").param("name", "Dale Seo").param("email", "dale.seo@gmail.com")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .accept(new MediaType("text", "plain", StandardCharsets.UTF_8))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testGetJson() throws Exception {
        mockMvc.perform(
                get("/json?id=123&name=Dale%20Seo&email=dale.seo@gmail.com")
                        .header(HttpHeaders.USER_AGENT, "Mozilla/4.0 (compatible; MSIE5.01; Windows NT)")
                        .header(HttpHeaders.HOST, "www.someclient.com")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testPostJson() throws Exception {
        String content = "{\n" +
                "  \"id\": 123,\n" +
                "  \"name\": \"Dale Seo\",\n" +
                "  \"email\": \"dale.seo@gmail.com\"\n" +
                "}";

        mockMvc.perform(
                post("/json")
                        .header(HttpHeaders.USER_AGENT, "Mozilla/4.0 (compatible; MSIE5.01; Windows NT)")
                        .header(HttpHeaders.HOST, "www.someclient.com")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @RestController
    private static class TestController {

        @RequestMapping(value = "/text")
        public String text(@RequestParam String id, @RequestParam String name, @RequestParam String email) {
            StringBuilder builder = new StringBuilder();
            builder.append("Your id is " + id + ".");
            builder.append("Your name is " + name + ".");
            builder.append("Your email is " + email + ".");
            return builder.toString();
        }

        @RequestMapping(value = "/json", method = RequestMethod.GET)
        public TestRes json(@RequestParam String id, @RequestParam String name, @RequestParam String email) {
            StringBuilder builder = new StringBuilder();
            builder.append("Your id is " + id + ".");
            builder.append("Your name is " + name + ".");
            builder.append("Your email is " + email + ".");
            System.out.println(builder.toString());
            return new TestRes(10000, builder.toString());
        }

        @RequestMapping(value = "/json", method = RequestMethod.POST)
        public TestRes json(@RequestBody TestReq req) {
            return new TestRes(10000, req.toString());
        }

    }

    private static class TestReq {
        private int id;
        private String name;
        private String email;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "TestReq{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    private static class TestRes {
        private int code;
        private String message;

        public TestRes(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}