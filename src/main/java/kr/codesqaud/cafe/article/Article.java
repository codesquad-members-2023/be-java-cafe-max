package kr.codesqaud.cafe.article;

/**
 * 게시글을 의미하는 객체
 */
public class Article {

    private final long id;
    private final String loginId;
    private final String title;
    private final String contents;
    private final boolean isDeleted;
    private final boolean hasReply;

    private Article(Builder builder) {
        id = builder.id;
        loginId = builder.loginId;
        title = builder.title;
        contents = builder.contents;
        isDeleted = builder.isDeleted;
        hasReply = builder.hasReply;
    }

    public long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public boolean getHasReply() {
        return hasReply;
    }

    public static class Builder {

        private long id;
        private String loginId;
        private String title;
        private String contents;
        private boolean isDeleted;
        private boolean hasReply;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder loginId(String loginId) {
            this.loginId = loginId;
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

        public Builder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public Builder hasReply(boolean hasReply) {
            this.hasReply = hasReply;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}
