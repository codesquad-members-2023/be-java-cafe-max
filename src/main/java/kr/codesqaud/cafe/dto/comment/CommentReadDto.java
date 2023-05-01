package kr.codesqaud.cafe.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.domain.Comment;

public class CommentReadDto {
    private final Long commentId;
    private final Long postId;
    private final String writer;
    private final String content;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private final LocalDateTime createDate;

    public CommentReadDto(Long commentId, Long postId, String writer, String content, LocalDateTime createDate) {
        this.commentId = commentId;
        this.postId = postId;
        this.writer = writer;
        this.content = content;
        this.createDate = createDate;
    }

    public static CommentReadDto from(Comment comment) {
        return new CommentReadDto(comment.getCommentId(), comment.getPostId(), comment.getWriter(), comment.getContent(), comment.getCreateDate());
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedTime() {
        return createDate;
    }
}
