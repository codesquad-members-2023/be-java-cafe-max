package kr.codesqaud.cafe.domain.reply.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.reply.entity.Reply;

public class ReplyDetailResponseDto {

	private String replyId;
	private String writer;
	private String content;
	private String dateTime;

	public ReplyDetailResponseDto(Reply reply) {
		this.replyId = String.valueOf(reply.getId());
		this.writer = reply.getWriter();
		this.content = reply.getContent();
		this.dateTime = setDateTime(reply.getDateTime());
	}

	public String getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public String getDateTime() {
		return dateTime;
	}

	private String setDateTime(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH:mm:ss"));

	}
}
