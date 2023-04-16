package kr.codesqaud.cafe.controller.dto.req;

public class ArticleEditRequest {

	private final String title;
	private final String content;

	public ArticleEditRequest(String title, String content) {
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
