package kr.codesqaud.cafe.board.dto;

import kr.codesqaud.cafe.board.domain.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentResponse {
    private Long commentId;
    private Long postId;
    private String writerId;
    private String writer;
    private String contents;
    private LocalDateTime writeDateTime;

    private CommentResponse(Long commentId, Long postId, String writerId, String writer, String contents, LocalDateTime writeDateTime) {
        this.commentId = commentId;
        this.postId = postId;
        this.writerId = writerId;
        this.writer = writer;
        this.contents = contents;
        this.writeDateTime = writeDateTime;
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment.getCommentId(), comment.getPostId(), comment.getWriterId(), comment.getWriter(), comment.getContents(), comment.getWriteDateTime());
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public String getWriterId() {
        return writerId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public String getWriteDateTime() {
        return writeDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
