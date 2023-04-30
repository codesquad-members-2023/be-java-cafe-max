package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Comment {

    private Long id;
    private final Long postId;
    private final Member writer;
    private final String content;
    private final LocalDateTime writeDateTime;

    public Comment(Long postId, Member writer, String content, LocalDateTime writeDateTime) {
        this(null, postId, writer, content, writeDateTime);
    }

    public Comment(Long id, Long postId, Member writer, String content, LocalDateTime writeDateTime) {
        this.id = id;
        this.postId = postId;
        this.writer = writer;
        this.content = content;
        this.writeDateTime = writeDateTime;
    }

    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public Member getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getWriteDateTime() {
        return writeDateTime;
    }

    public void injectionAutoIncrement(Long autoIncrement) {
        this.id = autoIncrement;
    }

    public boolean isSameWriterId(Long writerId) {
        return writer.equalsId(writerId);
    }
}
