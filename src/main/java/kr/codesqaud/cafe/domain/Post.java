package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Post {
    private Long postId;
    private final String title;
    private final String content;
    private final Long writerId;
    private final LocalDateTime writeDate;
    private Long views;

    public Post(String title, String content, Member writer, LocalDateTime writeDate, Long views) {
        this.title = title;
        this.content = content;
        this.writerId = writer.getMemberId();
        this.writeDate = writeDate;
        this.views = views;
    }

    public Post(Long postId, String title, String content, Long writerId, LocalDateTime writeDate, Long views) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
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

    public Long getWriterId() {
        return writerId;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getViews() {
        return views;
    }

    public void increaseViews(){
        views++;
    }

    public Post buildPost(Member writer) {
        return new Post(title, content, writer, writeDate, views);
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
