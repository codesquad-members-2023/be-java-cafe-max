package kr.codesqaud.cafe.dto;

public class ArticleDto {
	private String title;
	private String writer;
	private String contents;

	public ArticleDto(String title, String writer, String contents) {
		this.title = title;
		this.writer = writer;
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public String getWriter() {
		return writer;
	}

	public String getContents() {
		return contents;
	}
}
