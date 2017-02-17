package seo.dale.http.log.common;

public class LogConstants {

	/** 서버 요청 로그 접미어 */
	public static final String SERVER_REQUEST_LOG_PREFIX = "HTTP_SERVER_REQUEST_LOG";

	/** 서버 응답 로그 접미어 */
	public static final String SERVER_RESPONSE_LOG_PREFIX = "HTTP_SERVER_RESPONSE_LOG";

	/** 클라이언트 요청 로그 접미어 */
	public static final String CLIENT_REQUEST_LOG_PREFIX = "HTTP_CLIENT_REQUEST_LOG";

	/** 클라이언트 응답 로그 접미어 */
	public static final String CLIENT_RESPONSE_LOG_PREFIX = "HTTP_CLIENT_RESPONSE_LOG";

	/** 최대 스택 트레이스 */
	public static final int MAX_STACK_TRACE = 5;

	/** 예외 로그 메세지 포멧 */
	public static final String EXCEPTION_FORMAT = "throw exception CODE : %s, MESSAGE : %s";

	/** 에러 로그 메세지 포멧 */
	public static final String ERROR_FORMAT = "throw unexpected exception CODE : %s, MESSAGE : %s";

}
