package kr.codesqaud.cafe.controller.dto;

public class CommentRequest {
	private final String contents;

	public CommentRequest(String contents) {
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}
}
