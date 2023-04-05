package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {
	private String writer;
	private String title;
	private String contents;
	private Long id;
	private LocalDateTime createAt;

	public Article(String writer, String title, String contents) {
		this(writer, title, contents, null, LocalDateTime.now());
	}

	public Article(String writer, String title, String contents, Long id, LocalDateTime createAt) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.id = id;
		this.createAt = createAt;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
