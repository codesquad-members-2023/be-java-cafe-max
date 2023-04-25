package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Reply;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReplyDto {
    private final Long articleId;
    private final Long replyId;
    private final String userId;
    private final String userName;
    private final String comment;
    private final String createdAt;

    public ReplyDto(Long articleId, Long replyId, String userId, String userName, String comment, LocalDateTime createdAt) {
        this.articleId = articleId;
        this.replyId = replyId;
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.createdAt = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm").format(createdAt);
    }

    public static ReplyDto from(final Reply reply) {
        return new ReplyDto(reply.getArticleId(), reply.getReplyId(), reply.getUserId(), reply.getUserName(), reply.getComment(), reply.getCreatedAt());
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

    public String getCreatedAt() {
        return createdAt;
    }
}
