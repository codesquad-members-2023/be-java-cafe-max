package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Reply {
    private long id;
    private long articleId;
    private String userId;
    private String contents;
    private LocalDateTime createdAt;

    public Reply() {

    }

    public Reply(long articleId, String userId, String contents) {
        this.articleId = articleId;
        this.userId = userId;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isReplyWriter(final String userId) {
        return this.userId.equals(userId);
    }

    public long getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }
    public long getArticleId() { return articleId; }
    public String getContents() {
        return contents;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setId(final long id) {
        this.id = id;
    }
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    public void setArticleId(final long articleId) { this.articleId = articleId; }
    public void setContents(final String contents) {
        this.contents = contents;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
