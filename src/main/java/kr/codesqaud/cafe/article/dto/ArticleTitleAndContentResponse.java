package kr.codesqaud.cafe.article.dto;

public class ArticleTitleAndContentResponse {

	private final String title;
	private final String content;

	public ArticleTitleAndContentResponse(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

}
