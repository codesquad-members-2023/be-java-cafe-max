package kr.codesqaud.cafe.board.domain;

import java.time.LocalDateTime;

public class BoardPost {
    private final Long postId;
    private final String writerId;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime writeDateTime;

    private BoardPost(Builder builder) {
        this.postId = builder.postId;
        this.writerId = builder.writerId;
        this.writer = builder.writer;
        this.title = builder.title;
        this.contents = builder.contents;
        this.writeDateTime = builder.writeDateTime;
    }

    public Long getPostId() {
        return postId;
    }

    public String getWriterId() {
        return writerId;
    }

    public String getWriter() {
        return writer;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long postId;
        private String writerId;
        private String writer;
        private String title;
        private String contents;
        private LocalDateTime writeDateTime;

        public Builder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public Builder writerId(String writerId) {
            this.writerId = writerId;
            return this;
        }

        public Builder writer(String writer) {
            this.writer = writer;
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

        public BoardPost build() {
            return new BoardPost(this);
        }
    }
}
