package seo.dale.http.web.http;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HttpRequest {

	enum Method {
		GET, POST, PUT, DELETE
	}

	private Method method;

	private String url;

	private Map<String, List<String>> headers;

	private String body;

}
