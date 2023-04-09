package kr.codesqaud.cafe.dto.article;

import kr.codesqaud.cafe.domain.Article;

public class ArticleSaveRequest {
    private final String title;
    private final String writer;
    private final String contents;

    public ArticleSaveRequest(final String title, final String writer, final String contents) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
    }

    public Article toArticle() { // DTO → Entity
        return new Article(this.title, this.writer, this.contents);
    }

    public String getTitle() { return title; }
    public String getWriter() { return writer; }
    public String getContents() { return contents; }
}
