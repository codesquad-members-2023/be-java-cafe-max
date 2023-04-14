package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Post {

    private final Long id;
    private final String title;
    private final String content;
    private final Member writer;
    private final LocalDateTime writeDate;
    private final Long views;

    public Post(Long id, String title, String content, Member writer, LocalDateTime writeDate,
        Long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.writeDate = writeDate;
        this.views = views;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Member getWriter() {
        return writer;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getViews() {
        return views;
    }

    public Post increaseViews() {
        return new Post(id, title, content, writer, writeDate, views + 1);
    }

    public Post createWithId(Long id) {
        return new Post(id, title, content, writer, writeDate, views);
    }

    public boolean equalsWriterId(Long id) {
        return writer.equalsId(id);
    }
}
