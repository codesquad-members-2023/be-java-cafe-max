package kr.codesqaud.cafe.dto;

public class CommentUpdateRequest {
	private String comment;

	public CommentUpdateRequest(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}
}
