package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

public class ArticleDto {

    private final Long id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime writtenTime;

    public static class Builder {

        private Long id;
        private String writer;
        private String title;
        private String contents;
        private LocalDateTime writtenTime;

        public Builder id(Long id) {
            this.id = id;
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

        public Builder writtenTime(LocalDateTime writtenTime) {
            this.writtenTime = writtenTime;
            return this;
        }

        public ArticleDto build() {
            return new ArticleDto(this);
        }
    }

    private ArticleDto(Builder builder) {
        this.id = builder.id;
        this.writer = builder.writer;
        this.title = builder.title;
        this.contents = builder.contents;
        this.writtenTime = builder.writtenTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getWrittenTime() {
        return writtenTime;
    }
}
