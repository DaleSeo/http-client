package seo.dale.http.web.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seo.dale.http.client.HttpClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HttpService {

	@Autowired
	private HttpClient httpClient;

	public HttpResponse send(HttpRequest req) {
		Map<String, List<String>> headers = new HashMap<>();
		headers.put("Content-Type", Collections.singletonList("application/json; charset=utf-8"));

		HttpResponse res = new HttpResponse();
		res.setStatus("200 OK");
		res.setBody("Hi, there!");
		res.setHeaders(headers);

		return res;
	}

}
