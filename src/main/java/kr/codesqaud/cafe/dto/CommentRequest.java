package kr.codesqaud.cafe.dto;

public class CommentRequest {
	private Long articleIndex;
	private String author;
	private String comment;

	public CommentRequest(Long articleIndex, String author, String comment) {
		this.articleIndex = articleIndex;
		this.author = author;
		this.comment = comment;
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
}
