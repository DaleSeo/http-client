package seo.dale.http.log.server.extractor.body;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * application/x-www-form-urlencoded 컨텐트 타입에 대한 요청 바디 추출기
 * <pre>
 * application/x-www-form-urlencoded 컨텐트 타입으로 POST 요청이 들어온 경우,
 * Parameter을 만들기 위해 Tomcat 내부적으로 RequestInputStream을 모두 읽어서 소진시켜 버린다.
 * 따라서, 역으로 Prameter를 읽어들어 원래 요청 바디를 복원해준다.
 * </pre>
 */
public class FormUrlencodedRequestBodyExtractor implements RequestBodyExtractor {

	@Override
	public String extractBody(HttpServletRequest request, int maxLength) {
		StringBuilder builder = new StringBuilder();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			String[] values = request.getParameterValues(name);
			for (String value : values) {
				builder.append(name).append("=").append(value).append("&");
			}
		}
		if (builder.length() > 0) {
			builder.setLength(builder.length() - 1);
		}

		String formUrlencoded = builder.toString();

		int length = Math.min(formUrlencoded.length(), maxLength);

		return formUrlencoded.substring(0, length);
	}

}
