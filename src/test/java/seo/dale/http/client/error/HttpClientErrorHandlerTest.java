package seo.dale.http.client.error;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class HttpClientErrorHandlerTest {

	private HttpClientErrorHandler errorHandler = new HttpClientErrorHandler();

	@Test
	public void trueHasError() throws IOException {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		when(response.getStatusCode()).thenReturn(HttpStatus.OK);
		assertThat(errorHandler.hasError(response)).isFalse();
		verify(response).getStatusCode();
	}

	@Test
	public void falseHasError() throws IOException {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		when(response.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(errorHandler.hasError(response)).isTrue();
		verify(response).getStatusCode();
	}

	@Test(expected = HttpClientException.class)
	public void testHandleError() throws IOException {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		when(response.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			errorHandler.handleError(response);
			fail();
		} catch (HttpClientException e) {
			assertThat(e.getMessage()).isEqualTo("500 Internal Server Error");
			throw e;
		}
	}

}