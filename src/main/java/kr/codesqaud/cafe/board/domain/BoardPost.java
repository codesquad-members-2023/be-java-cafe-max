package kr.codesqaud.cafe.board.domain;

import java.time.LocalDateTime;

public class BoardPost {
    private Long postId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeDateTime;

    public BoardPost() {
    }

    // 점층적 생성자 패턴을 사용해봤습니다.
    public BoardPost(String writer, String title, String contents) {
        this(writer, title, contents, null);
    }

    public BoardPost(String writer, String title, String contents, Long postId) {
        this(writer, title, contents, postId, null);
    }

    public BoardPost(String writer, String title, String contents, Long postId, LocalDateTime writeDateTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.postId = postId;
        this.writeDateTime = writeDateTime;
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
