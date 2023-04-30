package kr.codesqaud.cafe.dto;

public class CommentResponse {

	private Long commentIndex;
	private Long articleIndex;
	private String author;
	private String comment;
	private String createdDate;
	private boolean deleted;

	public CommentResponse(Long commentIndex, Long articleIndex, String author, String comment, String createdDate,
		boolean deleted) {
		this.commentIndex = commentIndex;
		this.articleIndex = articleIndex;
		this.author = author;
		this.comment = comment;
		this.createdDate = createdDate;
		this.deleted = deleted;
	}

	public Long getArticleIndex() {
		return articleIndex;
	}

	public String getAuthor() {
		return author;
	}

	public String getComment() {
		return comment;
	}

	public Long getCommentIndex() {
		return commentIndex;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public boolean isDeleted() {
		return deleted;
	}
}
