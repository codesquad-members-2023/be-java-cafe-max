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
}
