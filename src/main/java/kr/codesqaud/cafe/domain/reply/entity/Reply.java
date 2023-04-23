package kr.codesqaud.cafe.domain.reply.entity;

import java.time.LocalDateTime;

public class Reply {
	private Long id;
	private Long articleId;
	private String content;
	private String writer;
	private LocalDateTime dateTime;

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Reply(Long articleId, String content, String writer) {
		this.articleId = articleId;
		this.content = content;
		this.writer = writer;
		this.dateTime = LocalDateTime.now();
	}

	public Reply() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
}
