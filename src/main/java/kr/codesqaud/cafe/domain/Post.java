package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Post {

    private final Long id;
    private final String title;
    private final String content;
    private final Member writer;
    private final LocalDateTime writeDateTime;
    private final Long views;
    private final Integer commentsSize;

    public Post(Long id, String title, String content) {
        this(id, title, content, null, null, null, 0);
    }

    public Post(String title, String content, Member writer, LocalDateTime writeDateTime) {
        this(null, title, content, writer, writeDateTime, 0L, 0);
    }

    public Post(Long id, String title, String content, Member writer, LocalDateTime writeDateTime,
        Long views, Integer commentsSize) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.writeDateTime = writeDateTime;
        this.views = views;
        this.commentsSize = commentsSize;
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

    public LocalDateTime getWriteDateTime() {
        return writeDateTime;
    }

    public Long getViews() {
        return views;
    }

    public Integer getCommentsSize() {
        return commentsSize;
    }

    public boolean equalsWriterId(Long writerId) {
        return writer.equalsId(writerId);
    }
}
