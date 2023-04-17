package kr.codesqaud.cafe.article.dto;

public class ArticleResponse {
	private final String title;

	private final String content;

	private final Long idx;

	private final String date;

	private String nickName;

	public ArticleResponse(String title, String content, Long idx, String date, String nickName) {
		this.title = title;
		this.content = content;
		this.idx = idx;
		this.date = date;
		this.nickName = nickName;
	}
}
