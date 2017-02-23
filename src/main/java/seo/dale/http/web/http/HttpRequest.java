package seo.dale.http.web.http;

import lombok.Data;

@Data
public class HttpRequest {

	enum Scheme {
		http, https
	}

	enum Method {
		GET, POST, PUT, DELETE
	}

	private Scheme scheme;
	private String host;
	private String path;
	private String query;
	private Method method;
	private String body;

	private String interfaceId;
	private String tenantId;
	private String systemId;

	private String authKey;
	private String secret;

}
