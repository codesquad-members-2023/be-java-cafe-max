package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.exception.DeniedArticleModificationException;

public class Article {
	private Long articleIndex;
	private String title;
	private String writer;
	private String contents;
	private String writeDate;
	private long hits;
	private boolean deleted;
	private String modDate;

	public Article(Long articleIndex, String title, String writer, String contents, String writeDate, long hits,
		boolean deleted) {
		this.articleIndex = articleIndex;
		this.title = title;
		this.writer = writer;
		this.contents = contents;
		this.writeDate = writeDate;
		this.hits = hits;
		this.deleted = deleted;
	}

	public Article(Long articleIndex, String title, String writer, String contents, String writeDate, Long hits) {
		this.articleIndex = articleIndex;
		this.title = title;
		this.writer = writer;
		this.contents = contents;
		this.writeDate = writeDate;
		this.hits = hits;
	}

	public Article(String title, String writer, String contents, String writeDate, Long hits) {
		this.title = title;
		this.writer = writer;
		this.contents = contents;
		this.writeDate = writeDate;
		this.hits = hits;
	}

	public Article(String writer) {
		this.writer = writer;
	}

	public Article(String title, String contents, String modDate) {
		this.title = title;
		this.contents = contents;
		this.modDate = modDate;
	}

	public Long getArticleIndex() {
		return articleIndex;
	}

	public String getTitle() {
		return title;
	}

	public String getWriter() {
		return writer;
	}

	public String getContents() {
		return contents;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public long getHits() {
		return hits;
	}

	public boolean setHits(long hits) {
		this.hits = hits;
		return true;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void validateWriter(String nickname) {
		if (!writer.equals(nickname)) {
			throw new DeniedArticleModificationException();
		}
	}

	public String getModDate() {
		return modDate;
	}
}
