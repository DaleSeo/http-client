package seo.dale.http.client.log.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

/**
 * 로깅 유틸리티
 */
public class LogUtils {

	/**
	 * 로그 이름으로 로거를 얻어와서 로그 레벨에 따라 로그 메세지를 출력한다.
	 */
	public static void logByLevel(String loggerName, String logLevel, String logMessage) {
		Logger logger = LoggerFactory.getLogger(loggerName);
		try {
			MethodUtils.invokeMethod(logger, StringUtils.lowerCase(logLevel), logMessage);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			logger.error("Request Logging Failed!");
		}
	}

	/**
	 * 바이트 배열로 부터 최대 길이 만큼만 읽는다.
	 */
	public static String readWithInMaxLength(byte[] bytes, int maxLength, boolean pretty) throws IOException {
		return readWithInMaxLength(new ByteArrayInputStream(bytes), maxLength, pretty);
	}

	/**
	 * 입력 스트림으로 부터 최대 길이 만큼만 읽는다.
	 */
	public static String readWithInMaxLength(InputStream in, int maxLength, boolean pretty) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (
				OutputStreamWriter writer = new OutputStreamWriter(out);
				InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)
		) {
			int ch, length = 0;
			while ((ch = reader.read()) != -1 && length < maxLength) {
				if (!pretty && (ch == '\n' || ch == '\r')) {
					continue;
				}
				writer.write(ch);
				length++;
			}
		}
		return out.toString();
	}

	/**
	 * StackStrace 없이 에러 로그를 남긴다.
	 * <li>첫 줄에 ErrorInfo 객체를 로깅하 때, "throw exception" 으로 시작한다.</li>
	 */
	public static void logErrorWithoutStackTrace(Logger logger, String errorCode, String errorMessage, Exception exception) {
		logger.error("Log Error Without StackTrace.");
		logger.error(formatErrorInfo(LogConstants.EXCEPTION_FORMAT, errorCode, errorMessage));
	}

	/**
	 * 제한된 에러 로그를 남긴다.
	 * <li>첫 줄에 ErrorInfo 객체를 로깅하 때, "throw exception" 으로 시작한다.</li>
	 * <li>마지막에 StackStrace를 5줄만 남긴다.</li>
	 */
	public static void logErrorWithLimitedStackTrace(Logger logger, String errorCode, String errorMessage, Exception exception) {
		StackTraceElement[] elements = exception.getStackTrace();
		logger.error("Log Error With Limited StackTrace. (StackTraceElements length : {})", elements.length);

		StringBuilder stringBuidler = new StringBuilder("");
		for (int i = 0; i < elements.length; i++) {
			if (i > LogConstants.MAX_STACK_TRACE) break;
			stringBuidler.append("\n\tat ");
			stringBuidler.append(elements[i]);
		}

		logger.error(formatErrorInfo(LogConstants.EXCEPTION_FORMAT, errorCode, errorMessage) + "\n{}{}", exception, stringBuidler.toString());
	}

	/**
	 * 완한한 에러 로그를 남긴다.
	 * <li>첫 줄에 ErrorInfo 객체를 로깅하 때, "throw unexpected exception" 으로 시작한다.</li>
	 * <li>마지막에 FullStackStrace를 남긴다.</li>
	 */
	public static void logErrorWithFullStackTrace(Logger logger, String errorCode, String errorMessage, Exception exception) {
		logger.error("Log Error With Full StackTrace.");
		logger.error(formatErrorInfo(LogConstants.ERROR_FORMAT, errorCode, errorMessage), exception);
	}

	/**
	 * ErrorInfo 객체를 포멧팅한다.
	 */
	public static String formatErrorInfo(String format, String errorCode, String errorMessage) {
		return String.format(format, errorCode, errorMessage);
	}


	/**
	 * 에러 로그를 쓰는 과정에서 발생 한 에러 로그를 쓴다.
	 */
	public static void logErrorOccurredDuringWritng(Logger logger, Exception ex) {
		logger.error("Error occurred during writing error.", ex);
	}

}
