package kr.codesqaud.cafe.app.comment.controller.dto;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.app.comment.entity.Comment;

public class CommentResponse implements Comparable<CommentResponse> {

    private final Long id;
    private final String content;
    private final LocalDateTime createTime;
    private final String writerName;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createTime = comment.getCreateTime();
        this.writerName = comment.getWriter().getName();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getWriterName() {
        return writerName;
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
            ", createTime=" + createTime +
            ", writerName='" + writerName + '\'' +
            '}';
    }
}
