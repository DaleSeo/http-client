package seo.dale.http.web.test;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private AtomicLong counter = new AtomicLong();
    private Map<Long, String> map = new HashMap<>();

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String get() {
        return map.toString();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public @ResponseBody String get(@PathVariable long id) {
        return map.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestBody String text) {
        map.put(counter.incrementAndGet(), text);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void put(@PathVariable long id, @RequestBody String text) {
        map.put(id, text);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        map.remove(id);
    }

}
