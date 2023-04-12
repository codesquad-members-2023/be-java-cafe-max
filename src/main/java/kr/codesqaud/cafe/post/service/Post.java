package kr.codesqaud.cafe.post.service;

import java.time.LocalDateTime;

public class Post {

    private final Long id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime writingTime;

    public Post(Long id, String writer, String title, String contents, LocalDateTime writingTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writingTime = writingTime;
    }

    public Post create(long id) {
        return new Post(id, this.writer, this.title, this.contents, this.writingTime);
    }

    public long getId() {
        return id;
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

    public LocalDateTime getWritingTime() {
        return writingTime;
    }
}
