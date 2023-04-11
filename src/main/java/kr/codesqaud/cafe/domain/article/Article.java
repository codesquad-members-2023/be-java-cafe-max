package kr.codesqaud.cafe.domain.article;


public class Article {
    private int index;
    private String userId;
    private String writer;
    private String title;
    private String contents;
    private String date;

    public static class Builder {
        private int index ;
        private String userId;
        private String writer;
        private String title;
        private String contents;

        private String date;

        public Builder index(int val) {
            index = val;
            return this;
        }
        public Builder date(String val) {
            date = val;
            return this;
        }
        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public Builder writer(String val) {
            writer = val;
            return this;
        }
        public Builder title(String val) {
            title = val;
            return this;
        }
        public Builder contents(String val) {
            contents = val;
            return this;
        }
        public Article build() {
            return new Article(this);
        }
    }

    private Article(Builder builder) {
        index = builder.index;
        userId = builder.userId;
        writer = builder.writer;
        title = builder.title;
        contents = builder.contents;
        date = builder.date;
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

    public int getIndex() {
        return index;
    }

    public String getUserId() {
        return userId;
    }
}
