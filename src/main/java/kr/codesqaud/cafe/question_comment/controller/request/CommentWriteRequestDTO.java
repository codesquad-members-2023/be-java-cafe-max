package kr.codesqaud.cafe.question_comment.controller.request;

import kr.codesqaud.cafe.question_comment.domain.CommentEntity;

public class CommentWriteRequestDTO {
	private String content;

	public CommentWriteRequestDTO(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public CommentEntity toEntity(long post_id, long writer_id, String writer) {
		return new CommentEntity(post_id, writer_id, writer, content);
	}
}
