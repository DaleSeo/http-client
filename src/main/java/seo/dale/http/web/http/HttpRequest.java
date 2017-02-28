package seo.dale.http.web.http;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HttpRequest {

	enum Scheme {
		HTTP, HTTPS
	}

	enum Method {
		GET, POST, PUT, DELETE
	}

	@Data
	static class Url {
		private Scheme scheme;
		private String host;
		private String path;
		private String query;
	}

	private Method method;

	private Url url;

	private Map<String, List<String>> headers;

	private String body;

}
