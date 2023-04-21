package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.exception.DeniedDataModificationException;

public class Article {
	private Long postIndex;
	private String title;
	private String writer;
	private String contents;
	private String writeDate;
	private long hits;
	private boolean deleted;

	public Article(Long postIndex, String title, String writer, String contents, String writeDate, long hits,
		boolean deleted) {
		this.postIndex = postIndex;
		this.title = title;
		this.writer = writer;
		this.contents = contents;
		this.writeDate = writeDate;
		this.hits = hits;
		this.deleted = deleted;
	}

	public Article(Long postIndex, String title, String writer, String contents, String writeDate, Long hits) {
		this.postIndex = postIndex;
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

	public Long getPostIndex() {
		return postIndex;
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

	public void validateWriter(String nickname, String message) {
		if (!writer.equals(nickname)) {
			throw new DeniedDataModificationException(message);
		}
	}
}
