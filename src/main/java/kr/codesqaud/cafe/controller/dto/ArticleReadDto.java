package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

import java.time.LocalDateTime;

public class ArticleReadDto {
    private final String title;
    private final String userId;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ArticleReadDto(String title, String userId, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.userId = userId;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ArticleReadDto(Article article) {
        this(article.getTitle(), article.getUserId(), article.getContents(), article.getCreatedAt(), article.getUpdatedAt());
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
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
}
