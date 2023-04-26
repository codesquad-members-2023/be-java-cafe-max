package kr.codesqaud.cafe.app.comment.controller.dto;

import kr.codesqaud.cafe.app.comment.entity.Comment;
import kr.codesqaud.cafe.util.LocalDateTimeUtil;

public class CommentResponse implements Comparable<CommentResponse> {

    private final Long id;
    private final String content;
    private final String createTime;
    private final String writerName;
    private final Long userId;
    private final Long questionId;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createTime = LocalDateTimeUtil.formatLocalDateTime(comment.getCreateTime());
        this.writerName = comment.getWriter().getName();
        this.userId = comment.getWriter().getId();
        this.questionId = comment.getQuestion().getId();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getWriterName() {
        return writerName;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    @Override
    public int compareTo(CommentResponse dto) {
        return dto.getCreateTime().compareTo(this.createTime);
    }

    @Override
    public String toString() {
        return "CommentResponse{" +
            "id=" + id +
            ", content='" + content + '\'' +
            ", createTime='" + createTime + '\'' +
            ", writerName='" + writerName + '\'' +
            ", userId=" + userId +
            ", questionId=" + questionId +
            '}';
    }
}
