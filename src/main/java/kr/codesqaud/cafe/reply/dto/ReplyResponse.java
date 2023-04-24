package kr.codesqaud.cafe.reply.dto;

public class ReplyResponse {
	private final String nickName;

	private final String content;

	private final String date;

	private final Long articleIdx;

	private final Long replyIdx;

	public ReplyResponse(String nickName, String content, String date, Long articleIdx, Long replyIdx) {
		this.nickName = nickName;
		this.content = content;
		this.date = date;
		this.articleIdx = articleIdx;
		this.replyIdx = replyIdx;
	}

	public String getNickName() {
		return nickName;
	}

	public String getContent() {
		return content;
	}

	public String getDate() {
		return date;
	}

	public Long getArticleIdx() {
		return articleIdx;
	}

	public Long getReplyIdx() {
		return replyIdx;
	}
}
