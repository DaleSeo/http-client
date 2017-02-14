package seo.dale.http.server.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 바디 보존 응답 래퍼
 */
public class ContentKeepingResponseWrapper extends HttpServletResponseWrapper {

	private ContentKeepingServletOutputStream servletOutputStream;

	public ContentKeepingResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (servletOutputStream == null) {
			servletOutputStream = new ContentKeepingServletOutputStream(super.getOutputStream());
		}
		return servletOutputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(new OutputStreamWriter(getOutputStream(), getCharacterEncoding()));
	}

	/**
	 * 보존된 응답 바디를 문자열 타입으로 반환한다.
	 */
	public String getContentAsString() {
		return new String(getContentAsByteArray(), Charset.forName(getCharacterEncoding()));
	}

	/**
	 * 보존된 응답 바디를 바이트 배열로 반환한다.
	 */
	public byte[] getContentAsByteArray() {
		if (servletOutputStream == null) {
			return new byte[0];
		}
		return servletOutputStream.toByteArray();
	}

	/**
	 * 응답 바디 보존을 위해 DelegatingServletOutputStream을 확장하여 구현
	 */
	private class ContentKeepingServletOutputStream extends ServletOutputStream {

		private final OutputStream targetStream;

		private ByteArrayOutputStream byteArrayOutputStream;

		public ContentKeepingServletOutputStream(OutputStream targetStream) {
			this.targetStream = targetStream;
			byteArrayOutputStream = new ByteArrayOutputStream();
		}

		public byte[] toByteArray() {
			return byteArrayOutputStream.toByteArray();
		}

		@Override
		public void write(int b) throws IOException {
			targetStream.write(b);
			byteArrayOutputStream.write(b);
		}

		@Override
		public void flush() throws IOException {
			super.flush();
			byteArrayOutputStream.flush();
		}

		@Override
		public void close() throws IOException {
			super.close();
			byteArrayOutputStream.close();
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener listener) {

		}

	}

}
