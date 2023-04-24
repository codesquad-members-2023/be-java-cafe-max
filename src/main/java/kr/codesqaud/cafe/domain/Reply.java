package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Reply {
    private Long id;
    private Long articleId;
    private Long userId;
    private String writer;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Reply(Long id, Long articleId, Long userId, String writer, String comments, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.writer = writer;
        this.comments = comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Reply(Long articleId, Long userId, String writer, String comments) {
        this(null, articleId, userId, writer, comments, null, null);
    }

    public Long getId() {
        return id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getWriter() {
        return writer;
    }

    public String getComments() {
        return comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
