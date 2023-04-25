package kr.codesqaud.cafe.article.dto;

public class ArticleResponseForList {
	private final String title;

	private final Long articleIdx;

	private final String date;

	private String nickName;

	public ArticleResponseForList(String title, Long articleIdx, String date, String nickName) {
		this.title = title;
		this.articleIdx = articleIdx;
		this.date = date;
		this.nickName = nickName;
	}
}
