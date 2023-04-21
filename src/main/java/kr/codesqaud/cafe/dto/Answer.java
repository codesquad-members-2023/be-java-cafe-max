package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

public class Answer {

    private Long id;
    private String userId;
    private Long articleId;
    private String contents;
    private LocalDateTime createdTime;

    public Answer() {
    }

    public Answer(Long id, String userId, Long articleId, String contents) {
        this.id = id;
        this.userId = userId;
        this.articleId = articleId;
        this.contents = contents;
        this.createdTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
