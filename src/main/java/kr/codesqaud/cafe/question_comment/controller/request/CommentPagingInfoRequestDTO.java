package kr.codesqaud.cafe.question_comment.controller.request;

public class CommentPagingInfoRequestDTO {
	private long cursor = 0;
	private final int size = 5;

	public CommentPagingInfoRequestDTO(long cursor) {
		this.cursor = cursor;
	}

	public long getCursor() {
		return cursor;
	}

	public int getSize() {
		return size;
	}
}
