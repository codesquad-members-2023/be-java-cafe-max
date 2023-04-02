package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Post {
    private final UUID postId;
    private final String title;
    private final String content;
    private final String writeId;
    private final LocalDateTime writeDate;
    private final Long views;

    public Post(UUID postId, String title, String content, String writeId, LocalDateTime writeDate, Long views) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writeId = writeId;
        this.writeDate = writeDate;
        this.views = views;
    }

    public UUID getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriteId() {
        return writeId;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getViews() {
        return views;
    }

    public Post of(UUID postId){
        return new Post(postId,title,content,writeId,writeDate,views);
    }
}
