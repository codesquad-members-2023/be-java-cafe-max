package kr.codesqaud.cafe.article.dto;

public class ReplyResponse {
	private final String nickName;

	private final String content;

	private final String date;

	public ReplyResponse(String nickName, String content, String date) {
		this.nickName = nickName;
		this.content = content;
		this.date = date;
	}
	
}
