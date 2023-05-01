package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Post {
    private Long id;
    private String title;
    private String content;
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

    public Post(Long id, String title, String content, String writerEmail, LocalDateTime writeDate, Long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerEmail = writerEmail;
        this.writeDate = writeDate;
        this.views = views;
    }

    public Long getid() {
        return id;
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

    public void setid(Long id) {
        this.id = id;
    }

    public void editPost(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}
