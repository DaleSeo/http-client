package seo.dale.http.client.resolve;

import org.springframework.http.MediaType;

import java.util.List;

public interface HeaderResolver {

	String resolveAuthKey();

	MediaType resolveContentType();

	List<MediaType> resolveAccept();

}
