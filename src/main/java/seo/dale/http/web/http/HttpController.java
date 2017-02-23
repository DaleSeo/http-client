package seo.dale.http.web.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/http")
public class HttpController {

	@Autowired
	private HttpService service;

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public @ResponseBody HttpResponse send(@RequestBody HttpRequest req) {
		return service.send(req);
	}

}
