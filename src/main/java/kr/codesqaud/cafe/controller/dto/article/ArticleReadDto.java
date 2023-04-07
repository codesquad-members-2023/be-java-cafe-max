package kr.codesqaud.cafe.controller.dto.article;

import kr.codesqaud.cafe.domain.Article;

import java.time.LocalDateTime;

public class ArticleReadDto {
    private final Long id;
    private final String title;
    private final String userId;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private ArticleReadDto(Long id, String title, String userId, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ArticleReadDto(Article article) {
        this(article.getId(), article.getTitle(), article.getUserId(), article.getContents(), article.getCreatedAt(), article.getUpdatedAt());
    }

    public Long getId() {
        return id;
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
