package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Post {
    private Long postId;
    private final String title;
    private final String content;
    private final String writerEmail;
    private final LocalDateTime writeDate;
    private Long views;

    public Post(String title, String content, String writer, LocalDateTime writeDate, Long views) {
        this.title = title;
        this.content = content;
        this.writerEmail = writer;
        this.writeDate = writeDate;
        this.views = views;
    }

    public Post(Long postId, String title, String content, String writerEmail, LocalDateTime writeDate, Long views) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writerEmail = writerEmail;
        this.writeDate = writeDate;
        this.views = views;
    }

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getViews() {
        return views;
    }

    public void increaseViews() {
        views++;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
