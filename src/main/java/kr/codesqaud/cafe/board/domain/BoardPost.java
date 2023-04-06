package kr.codesqaud.cafe.board.domain;

import kr.codesqaud.cafe.board.dto.PostResponse;

import java.time.LocalDateTime;

public class BoardPost {
    private Long postId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeDateTime;

    public BoardPost() {
    }

    public BoardPost(Long postId, String writer, String title, String contents, LocalDateTime writeDateTime) {
        this.postId = postId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeDateTime = writeDateTime;
    }

    public BoardPost(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public PostResponse toPostResponse() {
        return new PostResponse(postId, writer, title, contents, writeDateTime);
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getWriteDateTime() {
        return writeDateTime;
    }

    public void setWriteDateTime(LocalDateTime writeDateTime) {
        this.writeDateTime = writeDateTime;
    }
}
