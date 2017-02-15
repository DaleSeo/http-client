package seo.dale.http.log.server.extractor.body;

import org.springframework.web.util.WebUtils;
import seo.dale.http.server.wrapper.ContentKeepingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 기본 요청 바디 추출기
 */
public class DefaultRequestBodyExtractor implements RequestBodyExtractor {

	@Override
	public String extractBody(HttpServletRequest request, int maxLength) {
		ContentKeepingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentKeepingRequestWrapper.class);
		if (wrapper == null) {
			return "";
		}

		byte[] contentAsByteArray = wrapper.getContentAsByteArray();
		if (contentAsByteArray.length == 0) {
			return "";
		}

		int length = Math.min(contentAsByteArray.length, maxLength);

		String body;

		try {
			body = new String(contentAsByteArray, 0, length, request.getCharacterEncoding());
		}
		catch (UnsupportedEncodingException ex) {
			body = "[unknown]";
		}

		return body;
	}

}
