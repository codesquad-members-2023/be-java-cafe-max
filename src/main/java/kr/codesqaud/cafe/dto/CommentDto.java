package kr.codesqaud.cafe.dto;

public class CommentDto {
	private Long postIndex;
	private String author;
	private String comment;

	public CommentDto(Long postIndex, String author, String comment) {
		this.postIndex = postIndex;
		this.author = author;
		this.comment = comment;
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
}
