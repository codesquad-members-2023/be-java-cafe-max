package kr.codesqaud.cafe.article;

/**
 * 게시글을 의미하는 객체
 */
public class Article {

    private final long id;
    private final String writer;
    private final String title;
    private final String contents;

    private Article(Builder builder) {
        id = builder.id;
        writer = builder.writer;
        title = builder.title;
        contents = builder.contents;
    }

    public long getId() {
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

    public static class Builder {

        private long id;
        private String writer;
        private String title;
        private String contents;

        public Builder id(long id) {
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

        public Article build() {
            return new Article(this);
        }
    }
}
