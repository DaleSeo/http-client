package seo.dale.http.web.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/runtime")
    public void runtime() {
        throw new RuntimeException("This is an error~!!");
    }

}
