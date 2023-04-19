package kr.codesqaud.cafe.reply;

/**
 * 댓글을 의미하는 객체
 */
public class Reply {

    private final long id;
    private final long articleId;
    private final String userId;
    private final String contents;
    private final String createDateTime;

    private Reply(Builder builder) {
        id = builder.id;
        articleId = builder.articleId;
        userId = builder.userId;
        contents = builder.contents;
        createDateTime = builder.createDateTime;
    }

    public long getId() {
        return id;
    }

    public long getArticleId() {
        return articleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getContents() {
        return contents;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public static class Builder {

        private long id;
        private long articleId;
        private String userId;
        private String contents;
        private String createDateTime;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder articleId(long articleId) {
            this.articleId = articleId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public Builder createDateTime(String createDateTime) {
            this.createDateTime = createDateTime;
            return this;
        }

        public Reply build() {
            return new Reply(this);
        }
    }
}
