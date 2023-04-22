package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Reply {
    private Long id;
    private Long articleId;
    private String userId;
    private String replyContent;
    private LocalDateTime replyTime;

    public Reply() {
    }

    public Reply(Long articleId, String userId, String replyContent) {
        this.articleId = articleId;
        this.userId = userId;
        this.replyContent = replyContent;
        this.replyTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public LocalDateTime getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
    }
}
