package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Comment {

    private Long id;
    private final Long postId;
    private final Member writer;
    private final String content;
    private final LocalDateTime writeDateTime;

    private Comment(Long id, Long postId, Member writer, String content, LocalDateTime writeDateTime) {
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

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public static class CommentBuilder {

        private Long id;
        private Long postId;
        private Member writer;
        private String content;
        private LocalDateTime writeDate;

        public CommentBuilder() {

        }

        public Comment.CommentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Comment.CommentBuilder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public Comment.CommentBuilder writer(Member writer) {
            this.writer = writer;
            return this;
        }

        public Comment.CommentBuilder content(String content) {
            this.content = content;
            return this;
        }

        public Comment.CommentBuilder writeDate(LocalDateTime writeDate) {
            this.writeDate = writeDate;
            return this;
        }

        public Comment build() {
            return new Comment(id, postId, writer, content, writeDate);
        }
    }
}
