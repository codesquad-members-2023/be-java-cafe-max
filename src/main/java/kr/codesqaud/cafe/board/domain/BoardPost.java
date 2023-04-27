package kr.codesqaud.cafe.board.domain;

import kr.codesqaud.cafe.user.domain.User;

import java.time.LocalDateTime;

public class BoardPost {
    private final Long postId;
    private final String title;
    private final String contents;
    private final LocalDateTime writeDateTime;
    private final User writer;

    private BoardPost(Builder builder) {
        this.postId = builder.postId;
        this.title = builder.title;
        this.contents = builder.contents;
        this.writeDateTime = builder.writeDateTime;
        this.writer = builder.writer;
    }

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
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
        private Long postId;
        private String title;
        private String contents;
        private LocalDateTime writeDateTime;
        private User writer;

        public Builder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
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

        public BoardPost build() {
            return new BoardPost(this);
        }
    }
}
