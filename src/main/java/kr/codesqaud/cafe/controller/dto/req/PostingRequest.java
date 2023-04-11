package kr.codesqaud.cafe.controller.dto.req;

public class PostingRequest {

	private final String title;
	private final String contents;

	public PostingRequest(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}
}
