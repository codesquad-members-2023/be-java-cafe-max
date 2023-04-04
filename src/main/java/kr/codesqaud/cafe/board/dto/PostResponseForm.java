package kr.codesqaud.cafe.board.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostResponseForm {
    private Long postId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeDateTime;

    public PostResponseForm(Long postId, String writer, String title, String contents, LocalDateTime writeDateTime) {
        this.postId = postId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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
