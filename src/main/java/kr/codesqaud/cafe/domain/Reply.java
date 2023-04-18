package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Reply {

    private Long id;
    private String userId;
    private String writer;
    private Long articleId;
    private String contents;
    private LocalDateTime createdTime;
    private boolean deleted;

    public Reply(ReplyBuilder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.writer = builder.writer;
        this.articleId = builder.articleId;
        this.contents = builder.contents;
        this.createdTime = builder.createdTime;
        this.deleted = builder.deleted;
    }

    public static class ReplyBuilder {
        private Long id;
        private String userId;
        private String writer;
        private Long articleId;
        private String contents;
        private LocalDateTime createdTime;
        private boolean deleted;

        public ReplyBuilder(String userId, Long articleId) {
            this.userId = userId;
            this.articleId = articleId;
        }

        public ReplyBuilder setWriter(String writer) {
            this.writer = writer;
            return this;
        }

        public ReplyBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ReplyBuilder setContents(String contents) {
            this.contents = contents;
            return this;
        }

        public ReplyBuilder setCreatedTime(LocalDateTime createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public ReplyBuilder setDeleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Reply build() {
            return new Reply(this);
        }
    }

    public boolean isAuthor(String userId) {
        return this.userId.equals(userId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
