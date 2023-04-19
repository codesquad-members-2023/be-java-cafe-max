package kr.codesqaud.cafe.domain.reply.dto.request;

import kr.codesqaud.cafe.domain.reply.entity.Reply;

public class ReplySaveRequestDto {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	ReplySaveRequestDto(String content) {
		this.content = content;
	}

	public Reply toEntity(Long articleId, String writer) {
		return new Reply(articleId, this.content, writer);
	}
}
