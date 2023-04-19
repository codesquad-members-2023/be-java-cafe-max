package kr.codesqaud.cafe.reply.dto;

import javax.validation.constraints.NotBlank;

public class ReplyRequest {

	private String id;

	private Long articleIdx;

	private String nickName;

	@NotBlank
	private final String content;

	public ReplyRequest(String content) {
		this.content = content;
	}

	public void init(String id, String nickName, Long articleIdx) {
		this.id = id;
		this.nickName = nickName;
		this.articleIdx = articleIdx;
	}

	public String getId() {
		return id;
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
