package seo.dale.http.web.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seo.dale.http.client.HttpClient;
import seo.dale.http.client.model.HttpClientMethod;
import seo.dale.http.client.model.HttpClientRequest;
import seo.dale.http.client.model.HttpClientResponse;
import seo.dale.http.client.model.HttpClientUrl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HttpService {

	@Autowired
	private HttpClient httpClient;

	public HttpResponse send(HttpRequest req) {
		HttpClientMethod method = HttpClientMethod.valueOf(req.getMethod().name());
		HttpClientUrl url = HttpClientUrl.custom()
				.path(req.getUrl())
				.build();
		HttpClientRequest request = new HttpClientRequest(req.getHeaders(), req.getBody());

		HttpClientResponse<String> response = httpClient.exchange(method, url, request, String.class);

		Map<String, List<String>> headers = response.getHeaders();

		HttpResponse res = new HttpResponse();
		res.setStatus(response.getStatus().toString());
		res.setBody(response.getBody());
		res.setHeaders(headers);

		return res;
	}

}
