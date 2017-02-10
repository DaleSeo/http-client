package seo.dale.http.client.common;

/**
 * Http Client 공통 상수
 */
public class HttpClientConstants {

	/**
	 * 디폴트 총 커넥션 개수
	 */
	public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 2000;

	/**
	 * 디폴트 루트 당 최대 커넥션 개수
	 */
	public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 700;

	/**
	 * 디폴트 커넥트 타임아웃
	 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 2000; // 2 sec

	/**
	 * 디폴트 리드 타임아웃 (소켓 타임 아웃)
	 */
	public static final int DEFAULT_READ_TIMEOUT = 10000; // 10 sec

	/**
	 * 디폴트 요청 타임아웃 (커넥션풀로 부터 하나의 커넥션을 얻어올 때 타이아웃)
	 */
	public static final int DEFAULT_REQUEST_TIMEOUT = 1000; // 1 sec

	/**
	 * 디폴트 프로토콜
	 */
	public static final String DEFAULT_SCHEME = "http";

	/**
	 * 디폴트 포트
	 */
	public static final int DEFAULT_PORT = 80;

	/**
	 * 요청/응답 커스텀 헤더 상수 - 범용 고유 식별자
	 */
	public static final String HEADER_GUID = "x-guid";

	/**
	 * 요청 커스텀 헤더 상수 - 인증키
	 */
	public static final String HEADER_AUTH_KEY = "x-auth-key";

}
