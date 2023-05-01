package kr.codesqaud.cafe.board.dto;

import kr.codesqaud.cafe.board.domain.Comment;
import kr.codesqaud.cafe.user.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentResponse {
    private Long commentId;
    private Long postId;
    private String contents;
    private LocalDateTime writeDateTime;
    private User writer;

    private CommentResponse(Long commentId, Long postId, String contents, LocalDateTime writeDateTime, User writer) {
        this.commentId = commentId;
        this.postId = postId;
        this.contents = contents;
        this.writeDateTime = writeDateTime;
        this.writer = writer;
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment.getCommentId(), comment.getPostId(), comment.getContents(), comment.getWriteDateTime(), comment.getWriter());
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public String getContents() {
        return contents;
    }

    public String getWriteDateTime() {
        return writeDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public User getWriter() {
        return writer;
    }
}
