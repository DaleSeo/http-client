package seo.dale.http.log.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpServerLogger {

    void logRequest(HttpServletRequest request);

    void logResponse(HttpServletResponse response);

}
