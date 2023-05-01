package kr.codesqaud.cafe.board.domain;

import kr.codesqaud.cafe.user.domain.User;

import java.time.LocalDateTime;

public class Comment {
    private final Long commentId;
    private final Long postId;
    private final String contents;
    private final LocalDateTime writeDateTime;
    private final User writer;

    private Comment(Builder builder) {
        this.commentId = builder.commentId;
        this.postId = builder.postId;
        this.contents = builder.contents;
        this.writeDateTime = builder.writeDateTime;
        this.writer = builder.writer;
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getWriteDateTime() {
        return writeDateTime;
    }

    public User getWriter() {
        return writer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long commentId;
        private Long postId;
        private String contents;
        private LocalDateTime writeDateTime;
        private User writer;

        public Builder commentId(Long commentId) {
            this.commentId = commentId;
            return this;
        }

        public Builder postId(Long postId) {
            this.postId = postId;
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

        public Builder writer(User writer) {
            this.writer = writer;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
