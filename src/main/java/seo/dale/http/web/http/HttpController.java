package seo.dale.http.web.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/http")
public class HttpController {

	@Autowired
	private HttpService service;

	@Autowired
	private HttpHistoryService historyService;

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public @ResponseBody HttpResponse send(@RequestBody HttpRequest req) {
		return service.send(req);
	}

    // @RequestMapping(value = "/records", method = RequestMethod.OPTIONS)
    public @ResponseBody List<HttpRecord> options() {
        return historyService.findAll();
	}

	@RequestMapping(value = "/records", method = RequestMethod.GET)
	public @ResponseBody List<HttpRecord> findAll() {
		return historyService.findAll();
	}

	@RequestMapping(value = "/records", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void add(@RequestBody HttpRecord record) {
		historyService.add(record);
	}

}
