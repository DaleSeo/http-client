package seo.dale.http.web.http;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HttpResponse {

	private String status;

	private Map<String, List<String>> headers;

	private String body;

}
