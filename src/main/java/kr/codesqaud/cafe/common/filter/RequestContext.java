package kr.codesqaud.cafe.common.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.ContentCachingRequestWrapper;

@RequestScope
@Component
public class RequestContext {

	private ContentCachingRequestWrapper request;

	public void setRequest(final ContentCachingRequestWrapper request) {
		this.request = request;
	}

	public ContentCachingRequestWrapper getRequest() {
		return request;
	}
}
