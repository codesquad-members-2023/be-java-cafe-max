package kr.codesqaud.cafe.dto.article;

import kr.codesqaud.cafe.domain.Article;

public class ArticleSaveRequest {
    private String title;
    private String writer;
    private String contents;

    public ArticleSaveRequest(String title, String writer, String contents) {
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
