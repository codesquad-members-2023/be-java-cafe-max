package kr.codesqaud.cafe.controller.dto.article;

import kr.codesqaud.cafe.domain.Article;

import java.time.LocalDateTime;

public class ArticleReadDto {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String writer;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private ArticleReadDto(Long id, Long userId, String title, String writer, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ArticleReadDto(Article article) {
        this(article.getId(), article.getUserId(), article.getTitle(), article.getWriter(), article.getContents(), article.getCreatedAt(), article.getUpdatedAt());
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getUserId() {
        return userId;
    }
}
