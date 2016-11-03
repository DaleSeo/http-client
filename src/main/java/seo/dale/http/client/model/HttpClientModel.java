package seo.dale.http.client.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 기본 모델
 */
public class HttpClientModel {

	@Override
	public String toString() {
		return new ToStringBuilder(this).reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
