package kr.codesqaud.cafe.article.dto;

public class ArticleResponse {
	private final String title;

	private final String content;

	private final Long articleIdx;

	private final String date;

	private String nickName;

	public ArticleResponse(String title, String content, Long articleIdx, String date, String nickName) {
		this.title = title;
		this.content = content;
		this.articleIdx = articleIdx;
		this.date = date;
		this.nickName = nickName;
	}
}
