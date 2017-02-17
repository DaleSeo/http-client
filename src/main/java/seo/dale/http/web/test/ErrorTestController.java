package seo.dale.http.web.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/error")
public class ErrorTestController {

    @RequestMapping("/runtime")
    public void runtime() {
        throw new RuntimeException("This is an error~!!");
    }

}
