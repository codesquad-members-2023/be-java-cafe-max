package kr.codesqaud.cafe.board.domain;

import java.time.LocalDateTime;

public class Comment {
    private final Long commentId;
    private final Long postId;
    private final String writer;
    private final String contents;
    private final LocalDateTime writeDateTime;

    public Comment(Builder builder) {
        this.commentId = builder.commentId;
        this.postId = builder.postId;
        this.writer = builder.writer;
        this.contents = builder.contents;
        this.writeDateTime = builder.writeDateTime;
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getWriteDateTime() {
        return writeDateTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long commentId;
        private Long postId;
        private String writer;
        private String contents;
        private LocalDateTime writeDateTime;

        public Builder commentId(Long commentId) {
            this.commentId = commentId;
            return this;
        }

        public Builder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public Builder writer(String writer) {
            this.writer = writer;
            return this;
        }

        public Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public Builder writeDateTime(LocalDateTime writeDateTime) {
            this.writeDateTime = writeDateTime;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
