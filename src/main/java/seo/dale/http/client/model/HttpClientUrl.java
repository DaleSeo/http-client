package seo.dale.http.client.model;

import seo.dale.http.client.common.HttpClientConstants;

import java.util.Map;

/**
 * URL 모델
 */
public class HttpClientUrl extends HttpClientModel {

	public enum Scheme {
		HTTP, HTTPS;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	private Scheme scheme;
	private String host;
	private int port;
	private String path;
	private Map<String, ?> pathVars;

	private HttpClientUrl() {
	}

	public Scheme getScheme() {
		return scheme;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getPath() {
		return path;
	}

	public Map<String, ?> getPathVars() {
		return pathVars;
	}

	public static Builder custom() {
		return new Builder();
	}

	public static class Builder {

		private HttpClientUrl built;

		public Builder() {
			built = new HttpClientUrl();
			built.scheme = Scheme.HTTP;
			built.port = HttpClientConstants.DEFAULT_PORT;
		}

		public HttpClientUrl build() {
			return built;
		}

		public Builder scheme(Scheme scheme) {
			built.scheme = scheme;
			return this;
		}

		public Builder host(String host) {
			built.host = host;
			return this;
		}

		public Builder port(int port) {
			built.port = port;
			return this;
		}

		public Builder path(String path) {
			built.path = path;
			return this;
		}

		public Builder pathVars(Map<String, ?> pathVars) {
			built.pathVars = pathVars;
			return this;
		}

	}

}
