package kr.codesqaud.cafe.board.dto;

import kr.codesqaud.cafe.board.domain.BoardPost;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostResponse {
    private Long postId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeDateTime;

    public PostResponse(Long postId, String writer, String title, String contents, LocalDateTime writeDateTime) {
        this.postId = postId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeDateTime = writeDateTime;
    }

    public BoardPost toEntity() {
        return new BoardPost(writer, title, contents, postId);
    }

    public static PostResponse from(BoardPost boardPost) {
        return new PostResponse(boardPost.getPostId(), boardPost.getWriter(), boardPost.getTitle(), boardPost.getContents(), boardPost.getWriteDateTime());
    }

    public Long getPostId() {
        return postId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getWriteDateTime() {
        return writeDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
