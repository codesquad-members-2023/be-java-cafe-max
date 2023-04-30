package kr.codesqaud.cafe.controller.dto.reply;

import kr.codesqaud.cafe.domain.Reply;

public class ReplyCreateDto {
    private Long articleId;
    private Long userId;
    private String writer;
    private String comments;

    public ReplyCreateDto() {
    }

    public ReplyCreateDto(Long articleId, Long userId, String writer, String comments) {
        this.articleId = articleId;
        this.userId = userId;
        this.writer = writer;
        this.comments = comments;
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

    public Reply toReply() {
        return new Reply(articleId, userId, writer, comments);
    }
}
