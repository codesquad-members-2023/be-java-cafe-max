package kr.codesqaud.cafe.dto;

public class ArticleResponse {
	private Long articleIndex;
	private String title;
	private String writer;
	private String contents;
	private String writeDate;
	private Long hits;
	private Boolean deleted;

	public ArticleResponse(Long articleIndex, String title, String writer, String contents, String writeDate, Long hits,
		Boolean deleted) {
		this.articleIndex = articleIndex;
		this.title = title;
		this.writer = writer;
		this.contents = contents;
		this.writeDate = writeDate;
		this.hits = hits;
		this.deleted = deleted;
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

	public Long getArticleIndex() {
		return articleIndex;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public Long getHits() {
		return hits;
	}

	public Boolean isDeleted() {
		return deleted;
	}
}
