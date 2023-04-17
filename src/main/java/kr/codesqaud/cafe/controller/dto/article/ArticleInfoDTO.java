package kr.codesqaud.cafe.controller.dto.article;

public class ArticleInfoDTO {

	private final String title;
	private final String content;

	public ArticleInfoDTO(String title, String content) {
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
