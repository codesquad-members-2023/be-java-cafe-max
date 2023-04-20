package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.exception.DeniedDataModificationException;

public class Comment {
	private Long index;
	private Long postIndex;
	private String author;
	private String comment;
	private String createdDate;
	private boolean deleted;

	public Comment(Long index, Long postIndex, String author, String comment, String createdDate, boolean deleted) {
		this.index = index;
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

	public Long getIndex() {
		return index;
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

	public void validateAuthor(String nickname) {
		if (!author.equals(nickname)) {
			throw new DeniedDataModificationException("다른 사람의 댓글은 삭제할 수 없습니다.");
		}
	}

	public void validateAuthors(String nickname) {
		if (!author.equals(nickname)) {
			System.out.println(nickname);
			System.out.println(author);
			throw new DeniedDataModificationException("다른 사람의 댓글이 있으면 게시글을 삭제할 수 없습니다.");
		}
	}
}
