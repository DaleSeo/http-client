package seo.dale.http.server.wrapper;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 바디 보존 요청 래퍼
 */
public class ContentKeepingRequestWrapper extends HttpServletRequestWrapper {

	private byte[] content;

	private ServletInputStream inputStream;

	private BufferedReader reader;

	public ContentKeepingRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 보존된 요청 전문을 스트링 타입으로 반환한다.
	 */
	public String getContentAsString() {
		return new String(getContentAsByteArray(), Charset.forName(getCharacterEncoding()));
	}

	/**
	 * 보존된 요청 전문을 바이트 배열로 번환한다.
	 */
	public byte[] getContentAsByteArray() {
		if (content == null) {
			try {
				content = StreamUtils.copyToByteArray(getRequest().getInputStream());
			} catch (IOException e) {
				content = new byte[0];
			}
		}
		return content;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (content == null) {
			content = StreamUtils.copyToByteArray(getRequest().getInputStream());
		}
		if (inputStream == null) {
			inputStream = new ContentKeepingServletInputStream(new ByteArrayInputStream(content));
		}
		return inputStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		if (reader == null) {
			reader = new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
		}
		return reader;
	}

	private class ContentKeepingServletInputStream extends ServletInputStream {

		private final InputStream sourceStream;

		public ContentKeepingServletInputStream(InputStream sourceStream) {
			this.sourceStream = sourceStream;
		}

		@Override
		public int read() throws IOException {
			return this.sourceStream.read();
		}

		@Override
		public void close() throws IOException {
			super.close();
			this.sourceStream.close();
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener listener) {

		}

	}


}
