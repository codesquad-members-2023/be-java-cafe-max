package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.request.ReplyRequest;

import java.time.LocalDateTime;

public class Reply {
    private final Long articleId;
    private final Long replyId;
    private final String userId;
    private final String userName;
    private String comment;
    private final LocalDateTime createdAt;


    public Reply(Long articleId, Long replyId, String userId, String userName, String comment, LocalDateTime createdAt) {
        this.articleId = articleId;
        this.replyId = replyId;
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    private Reply(Long articleId, String userId, String userName, String comment, LocalDateTime createdAt) {
        this(articleId, null, userId, userName, comment, createdAt);
    }

    public static Reply from(Long articleId, String userId, String userName, final ReplyRequest replyRequest) {
        return new Reply(
                articleId,
                userId,
                userName,
                replyRequest.getComment(),
                LocalDateTime.now());
    }

    public Long getArticleId() {
        return articleId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void editReply(final String comment) {
        this.comment = comment;
    }
}
