package seo.dale.http.client.log.common;

/**
 * 로깅 상수
 */
public class LogConstants {

	/** 클라이언트 요청 로그 접미어 */
	public static final String REQUEST_LOG_PREFIX = "CLIENT_REQUEST_LOGGING";

	/** 클라이언트 응답 로그 접미어 */
	public static final String RESPONSE_LOG_PREFIX = "CLIENT_RESPONSE_LOGGING";

	/** 최대 스택 트레이스 */
	public static final int MAX_STACK_TRACE = 5;

	/** 예외 로그 메세지 포멧 */
	public static final String EXCEPTION_FORMAT = "throw exception CODE : %s, MESSAGE : %s";

	/** 에러 로그 메세지 포멧 */
	public static final String ERROR_FORMAT = "throw unexpected exception CODE : %s, MESSAGE : %s";

	/** 디폴트 로거 이름 */
	public static final String DEFAULT_LOGGER_NAME = "HTTP_CLIENT_LOGGER";

	/** 로그 메세지 접두어 */
	public static final String LOG_MESSAGE_PREFIX = "HTTP";

}
