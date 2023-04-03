package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.article.ArticleSaveRequest;
import kr.codesqaud.cafe.dto.article.ArticleUpdateRequest;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String title;
    private String writer;
    private String contents;
    private final LocalDateTime createdAt;

    public Article(String title, String writer, String contents) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
    }

    private Article(Long id, String title, String writer, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public static Article from(ArticleSaveRequest articleSaveRequest) { // DTO → Entity
        return new Article(articleSaveRequest.getTitle(), articleSaveRequest.getWriter(), articleSaveRequest.getContents());
    }

    public static Article from(ArticleUpdateRequest articleUpdateRequest, LocalDateTime createdAt) { // DTO → Entity
        return new Article(articleUpdateRequest.getId(),
                articleUpdateRequest.getTitle(),
                articleUpdateRequest.getWriter(),
                articleUpdateRequest.getContents(),
                createdAt);
    }

    public Long getId() {
        return id;
    }
    public String getTitle() { return title; }
    public String getWriter() { return writer; }
    public String getContents() { return contents; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setIdUsingSequence(Long sequence) {
        this.id = sequence;
    }
}
