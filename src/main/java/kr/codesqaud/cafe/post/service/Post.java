package kr.codesqaud.cafe.post.service;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {

    private final Long id;
    private final String writerId;
    private final String writerName;
    private final String title;
    private final String contents;
    private final LocalDateTime writingTime;
    private final boolean isDeleted;

    public Post(Long id, String writerId, String writerName, String title, String contents, LocalDateTime writingTime, boolean isDeleted) {
        this.id = id;
        this.writerId = writerId;
        this.writerName = writerName;
        this.title = title;
        this.contents = contents;
        this.writingTime = writingTime;
        this.isDeleted = isDeleted;
    }

    public Post create(long id) {
        return new Post(id, this.writerId, this.writerName, this.title, this.contents, this.writingTime, this.isDeleted);
    }

    public long getId() {
        return id;
    }

    public String getWriterId() {
        return writerId;
    }

    public String getWriterName() {
        return writerName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id)
                && writerId.equals(post.writerId)
                && writerName.equals(post.writerName)
                && title.equals(post.title)
                && contents.equals(post.contents)
                && writingTime.equals(post.writingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, writerId, writerName, title, contents, writingTime);
    }
}
