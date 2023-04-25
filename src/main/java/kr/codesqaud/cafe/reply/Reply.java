package kr.codesqaud.cafe.reply;

/**
 * 댓글을 의미하는 객체
 */
public class Reply {

    private final long id;
    private final long articleId;
    private final String loginId;
    private final String contents;
    private final String createDateTime;

    private Reply(Builder builder) {
        id = builder.id;
        articleId = builder.articleId;
        loginId = builder.loginId;
        contents = builder.contents;
        createDateTime = builder.createDateTime;
    }

    public long getId() {
        return id;
    }

    public long getArticleId() {
        return articleId;
    }

    public String getLoginId() {
        return loginId;
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
        private String loginId;
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

        public Builder userId(String loginId) {
            this.loginId = loginId;
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
