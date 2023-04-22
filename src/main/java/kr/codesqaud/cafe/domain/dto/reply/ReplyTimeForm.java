package kr.codesqaud.cafe.domain.dto.reply;

import kr.codesqaud.cafe.domain.Reply;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReplyTimeForm {
    private Long id;
    private Long articleId;
    private String userId;
    private String replyContent;
    private String replyTime;

    public ReplyTimeForm() {
    }

    private ReplyTimeForm(Long id, Long articleId, String userId, String replyContent, LocalDateTime replyTime) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.replyContent = replyContent;
        this.replyTime = replyTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static ReplyTimeForm from(Reply reply) {
        return new ReplyTimeForm(reply.getId(), reply.getArticleId(), reply.getUserId(), reply.getReplyContent(), reply.getReplyTime());
    }

    public static ReplyTimeForm from(Long replyId, Reply reply) {
        return new ReplyTimeForm(replyId, reply.getArticleId(), reply.getUserId(), reply.getReplyContent(), reply.getReplyTime());
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

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
}
