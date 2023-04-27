package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Reply {

    private final Long id;
    private final Long articleId;
    private final String writer;
    private final String contents;
    private final LocalDateTime writtenTime;

    public static class Builder {

        private Long id;
        private Long articleId;
        private String writer;
        private String contents;
        private LocalDateTime writtenTime;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder articleId(long articleId) {
            this.articleId = articleId;
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

        public Builder writtenTime(LocalDateTime writtenTime) {
            this.writtenTime = writtenTime;
            return this;
        }

        public Reply build() {
            return new Reply(this);
        }
    }

    public Reply(Builder builder) {
        this.id = builder.id;
        this.articleId = builder.articleId;
        this.writer = builder.writer;;
        this.contents = builder.contents;
        this.writtenTime = builder.writtenTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public Long getArticleId() { return articleId;}

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getWrittenTime() {
        return writtenTime;
    }
}
