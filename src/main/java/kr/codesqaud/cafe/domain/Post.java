package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Post {

    private final Long id;
    private final String title;
    private final String content;
    private final String writerId;
    private final LocalDateTime writeDate;
    private final Long views;

    public Post(Long id, String title, String content, String writerId, LocalDateTime writeDate,
        Long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
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

    public String getWriterId() {
        return writerId;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getViews() {
        return views;
    }

    public Post of(Long id) {
        return new Post(id, title, content, writerId, writeDate, views);
    }
}
