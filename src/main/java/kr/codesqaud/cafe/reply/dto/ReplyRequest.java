package kr.codesqaud.cafe.reply.dto;

import javax.validation.constraints.NotBlank;

public class ReplyRequest {

	private String userId;

	private Long articleIdx;

	private String nickName;

	@NotBlank
	private final String content;

	public ReplyRequest(String content, String nickName) {
		this.content = content;
		this.nickName = nickName;
	}

	public void init(String userId, Long articleIdx) {
		this.userId = userId;
		this.articleIdx = articleIdx;
	}

	public String getUserId() {
		return userId;
	}

	public Long getArticleIdx() {
		return articleIdx;
	}

	public String getNickName() {
		return nickName;
	}

	public String getContent() {
		return content;
	}
}
