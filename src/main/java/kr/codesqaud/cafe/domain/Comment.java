package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.exception.DeniedDataModificationException;

public class Comment {
	private Long commentIndex;
	private Long postIndex;
	private String author;
	private String comment;
	private String createdDate;
	private boolean deleted;

	public Comment(Long commentIndex, Long postIndex, String author, String comment, String createdDate,
		boolean deleted) {
		this.commentIndex = commentIndex;
		this.postIndex = postIndex;
		this.author = author;
		this.comment = comment;
		this.createdDate = createdDate;
		this.deleted = deleted;
	}

	public Comment(Long postIndex, String author, String comment, String createdDate, boolean deleted) {
		this.postIndex = postIndex;
		this.author = author;
		this.comment = comment;
		this.createdDate = createdDate;
		this.deleted = deleted;
	}

	public Long getCommentIndex() {
		return commentIndex;
	}

	public Long getPostIndex() {
		return postIndex;
	}

	public String getAuthor() {
		return author;
	}

	public String getComment() {
		return comment;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void validateAuthor(String nickname, String message) {
		if (!author.equals(nickname)) {
			throw new DeniedDataModificationException(message);
		}
	}
}
